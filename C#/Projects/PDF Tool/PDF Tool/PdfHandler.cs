using System;
using System.Collections.Generic;
using System.Collections;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Windows.Forms;
using iText;
using iText.Kernel.Utils;
using iText.Kernel.Pdf;
using iText.Layout;
using iText.Bouncycastleconnector;
using iText.Bouncycastle;
using System.Diagnostics;
using System.Text.RegularExpressions;
using iText.Signatures;
using System.Linq.Expressions;


namespace PDF_Tool
{
    // Cannot grab numbers from provided page range
    class PageCastException: Exception
    {
        public PageCastException(string message) : base(message){  }  
    }
    // Invalid format entered for page range Ex: 2 - 1 rather than 1 - 2
    class PageEntryException : Exception
    {
        public PageEntryException(string message): base(message){ }
    }
    // Invalid entry from user on split interval
    class SplitEntryException: Exception
    {
        string message;
        public SplitEntryException(string message) : base(message) { this.message = message; }
    }
    

    // Handles all pdf operations
    internal class PdfHandler
    {
        // Custom Pdf splitter class as per IText documentation
        private class CustomSplitter : PdfSplitter
        {
            private string dest;
            private int partNum = 1;

            public CustomSplitter(PdfDocument pdfDocument, string dest) : base(pdfDocument)
            {
                this.dest = dest;
            }

            protected override PdfWriter GetNextPdfWriter(PageRange documentPageRange)
            {
                return new PdfWriter(String.Format(dest, partNum++));
            }

        }
        public PdfHandler()
        {
        }

        // Checks page entry to ensure it is valid
        public bool ValidPageEntry(string entry)
        {
           
            string pattern = @"^[0-9]{1,14}-[0-9]{1,14}$";
            entry = entry.Replace(" ", "");

            return Regex.IsMatch(entry,pattern);
        }
        // Get a list of all paths in PDFs list
        public List<string> GetPaths(IList<FilePathObject> pdfs)
        {
            
            List<string> paths = new List<string>();
            foreach (FilePathObject pdf in pdfs) 
                paths.Add(pdf.Path);
            return paths;
        }
        // Removes all selected PDFs from listbox
        public void RemovePdfs(IList items, ICollection<FilePathObject> pdfs) 
        {
            // Find items in list and remove them
            foreach (var item in items)
                foreach (var pdf in pdfs)
                {
                    if (pdf.Path.Equals(item)) 
                    { 
                        pdfs.Remove(pdf);
                        break;
                    }
                }
        }
        // Adds selected PDFs to listbox
        public bool AddPdfs(ICollection<FilePathObject> pdfs)
        {
            bool duplicateFile = false; 
            List<string> files = new List<string>();

            using (OpenFileDialog openFileDialog = new OpenFileDialog())
            {
                // Filters all files that aren't pdf
                openFileDialog.Filter = "PDF files (*.PDF)|*.PDF";
                openFileDialog.FilterIndex = 1;
                openFileDialog.RestoreDirectory = true;
                // Allows multiple pdfs to be selected
                openFileDialog.Multiselect = true;
                // Go through and add files picked to list
                if (openFileDialog.ShowDialog() == DialogResult.OK)
                {
                    foreach (string file in openFileDialog.FileNames)
                        files.Add(file);
                }
            }
           
            // Get list of paths in a more convient way
            List<string> paths = GetPaths((IList<FilePathObject>)pdfs);
            // Add file paths to list if they are not already in the list
            foreach (var file in files)
            {                
                FilePathObject newPdf = new FilePathObject(file);
                if(!paths.Contains(file))
                    pdfs.Add(newPdf);
                else
                    duplicateFile = true;
            }

            return duplicateFile;
        }

        // Move position of selected file up by one in listbox
        public void ShiftUp(FilePathObject item, IList<FilePathObject> pdfs, int index)
        {
            if (index <= 0)
                return;
            FilePathObject temp = pdfs[index - 1];
            pdfs[index - 1] = item;
            pdfs[index] = temp;
          
        }

        // Move position of selected file down by one in listbox
        public void ShiftDown(FilePathObject item, IList<FilePathObject> pdfs, int index) 
        {
            if (index >= pdfs.Count - 1 || index == -1)
                return;
            FilePathObject temp = pdfs[index + 1];
            pdfs[index + 1] = item;
            pdfs[index] = temp;
        }
       
        // Merges all PDFs in the listbox
        public int Combine(ICollection<FilePathObject> filesToMerge)
        {
            
            // Prevent errors on nothing to merge
            if(filesToMerge.Count == 0)
                return -1;

            string dest = string.Empty;
            using (SaveFileDialog saveFileDialog = new SaveFileDialog())
            {
                // Opens the file dialog on the C: drive
                //folderBrowserDialog.RootFolder;
                saveFileDialog.Filter = "(*.pdf)|*.pdf|All files (*.*)|*.*";
               
                // Go through and add files picked to list
                if (saveFileDialog.ShowDialog() == DialogResult.OK)
                    dest = saveFileDialog.FileName;
            }
  
            // Create pdf objects to open pdfs and merge
            PdfWriter writer = null;
            PdfDocument pdf = null;
            PdfMerger merger = null;

            try
            {
                writer = new PdfWriter(dest);
                pdf = new PdfDocument(writer);
                merger = new PdfMerger(pdf);
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message);
                return 0;
            }
            // List to fill with pdf objects
            List<PdfDocument> pdfDocuments = new List<PdfDocument>();
       
            try
            {
                // Go through each file in filebox
                foreach (FilePathObject item in filesToMerge)
                {

                    string pagesEntry = item.Pages;
                    PdfDocument pdfSource = new PdfDocument(new PdfReader(item.Path));
                    // Check for custom page range and combine
                    if (!pagesEntry.Equals(""))
                    {
                        pagesEntry = pagesEntry.Replace(" ", "");
                        int pageOne, pageTwo;
                        // Check that page ranges are valid
                        if (!int.TryParse(pagesEntry.Substring(0, pagesEntry.IndexOf("-")), out pageOne))
                        {
                            throw new PageCastException("Page cast failed");
                        }
                        if (!int.TryParse(pagesEntry.Substring(pagesEntry.IndexOf("-")+1), out pageTwo))
                        {
                            throw new PageCastException("Page cast failed");
                        }
                        if (pageTwo > pdfSource.GetNumberOfPages())
                            throw new PageEntryException("Page entry failed");
                        else if (pageOne < 0)
                        {
                            throw new PageEntryException("Page entry failed");
                        }


                        merger.Merge(pdfSource, pageOne, pageTwo);
                    }
                    else
                        merger.Merge(pdfSource, 1, pdfSource.GetNumberOfPages());

                    pdfSource.Close();
                }
            // Check for invaild page range
            }catch (PageCastException ex)
            {
                Debug.WriteLine(ex.Message);
                return 2;
            }// Check for invalid page entry
            catch (PageEntryException ex)
            {
                Debug.WriteLine(ex.Message);
                return 3;
            }// Catch any other issue that has not been specified
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message);
                return 4;
            }
            
            pdf.Close();
            return 1;
        }
        // Split pdf provided
        public int Split(string interval)
        {
           // Get file location of file to split
            string sourceDest = string.Empty;
            using (OpenFileDialog openFileDialog = new OpenFileDialog())
            {
                openFileDialog.Filter = "(*.pdf)|*.pdf|All files (*.*)|*.*";
                openFileDialog.Multiselect = false;
                if(openFileDialog.ShowDialog() == DialogResult.OK)
                {
                   sourceDest = openFileDialog.FileName;
                }
            }
            // Get the folder location as to wehere to save split documents
            string saveDest = string.Empty;
            using (FolderBrowserDialog folderBrowserDialog = new FolderBrowserDialog())
            {
                // Go through and add files picked to list
                if (folderBrowserDialog.ShowDialog() == DialogResult.OK) {
                    saveDest = folderBrowserDialog.SelectedPath;
                    saveDest = string.Concat(saveDest, "\\Split_Document_{0}.pdf");

                    }
            }
            // Initialze pdf objects to access source pdf
            PdfReader reader = new PdfReader(sourceDest);
            PdfDocument pdf = new PdfDocument(reader);

            try
            {
                // Ensure that the added interval is of the valid format
                interval = interval.Trim();
                int numPages = 0;
                if((!int.TryParse(interval, out numPages)) || numPages <= 0)
                    throw new SplitEntryException(interval + " is an invalid interval");
            

                // Fill list of pages to split by
                List<int> pageNums = new List<int>();
                for (int i = 1; i <= pdf.GetNumberOfPages(); i+=numPages)
                    pageNums.Add(i);
                // In the event there is nothing in the pages list, throw invalid entry as an empty list will crash SplitByPageNumbers()
                if(pageNums.Count == 0)
                        throw new SplitEntryException(interval + " is an invalid interval");

                // Split files and close them

                IList<PdfDocument> splitPdfs = new CustomSplitter(pdf, saveDest).SplitByPageNumbers(pageNums);
                foreach (PdfDocument doc in splitPdfs)
                {
                    doc.Close();
                }
            }
            catch (SplitEntryException ex)
            {
                Debug.WriteLine(ex.Message);
                return 2;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message);
                return 3;
            }
            pdf.Close();
            
            return 1;
        }
    
    }
}
