using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.ScrollBar;
using System.Security.Cryptography;

namespace PDF_Tool
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private List<FilePathObject> filePathObjects = new List<FilePathObject>();
        private PdfHandler handler = new PdfHandler();
        public MainWindow()
        {
            InitializeComponent();
        }

        private void UpdateFileBox()
        {
            string[] items = new string[filePathObjects.Count];
            for (int i = 0; i < items.Length; i++)
                items[i] = filePathObjects[i].Path;

            FileBox.ItemsSource = items;
            FileBox.Items.Refresh();
        }
        private void Remove_Click(object sender, RoutedEventArgs e)
        {

            handler.RemovePdfs(FileBox.SelectedItems, filePathObjects);
            CombineStatusLb.Content = "Selected files have been removed.";
            UpdateFileBox();
        }

        private void Add_Click(object sender, RoutedEventArgs e)
        {

            if (handler.AddPdfs(filePathObjects))
                CombineStatusLb.Content = "Duplicate files not added.";
            else
                CombineStatusLb.Content = "Files added.";
            UpdateFileBox();
        }

        private void Up_Click(object sender, RoutedEventArgs e)
        {
            FilePathObject selectedItem = new FilePathObject((string)FileBox.SelectedItem, PagesBox.Text);

            handler.ShiftUp(selectedItem, filePathObjects, FileBox.SelectedIndex);
            UpdateFileBox();
        }

        private void Down_Click(object sender, RoutedEventArgs e)
        {
            FilePathObject selectedItem = new FilePathObject((string)FileBox.SelectedItem, PagesBox.Text);

            handler.ShiftDown(selectedItem, filePathObjects, FileBox.SelectedIndex);
            UpdateFileBox();
        }

        private void CombineBtn_Click(object sender, RoutedEventArgs e)
        {
            // Combine files
            switch (handler.Combine(filePathObjects))
            {
                case -1:
                    CombineStatusLb.Content = "No files to merge";
                    break;
                case 0:
                    CombineStatusLb.Content = "Error opening file";
                    break;
                case 1:
                    CombineStatusLb.Content = "Merged successfully";
                    break;
                case 2:
                    CombineStatusLb.Content = "Page format incorrect";
                    break;
                case 3:
                    CombineStatusLb.Content = "Invalid page numbers entered. Check PDFs";
                    break;
                case 4:
                    CombineStatusLb.Content = "Error merging files. Check PDFs";
                    break;
            }
        }

        private void FileBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            // Resets operation label when selecting 
            CombineStatusLb.Content = "Awaiting Operation";
            // When more than one file is selected, turn off movement and pages
            if (FileBox.SelectedItems.Count > 1)
            {
                UpBtn.IsEnabled = false;
                DownBtn.IsEnabled = false;
                PagesBox.Clear();
                PagesBox.IsEnabled = false;
                
            }
            else
            {
                // When only one file is selected allow moving position of files
                UpBtn.IsEnabled = true;
                DownBtn.IsEnabled = true;
                // As long as there is a file selected, allow for entry
                if (FileBox.SelectedItems.Count > 0)
                {
                    PagesBox.IsEnabled = true;
                    PagesBox.Text = filePathObjects[FileBox.SelectedIndex].Pages;
                }
                // If there is no file selected, turn off the pages box
                else if(FileBox.SelectedItems.Count == 0)
                {
                    PagesBox.Clear();
                    PagesBox.IsEnabled = false;
                }
            }
        }

        private void SplitBtn_Click(object sender, RoutedEventArgs e)
        {
            //Split pdf
            switch (handler.Split(SplitIntervalBox.Text))
            {
                case 1:
                    SplitStatusLb.Content = "Split document successfully";
                    break;
                case 2:
                    SplitStatusLb.Content = "Split interval is invalid. Enter a valid interval";
                    break;
                case 3:
                    SplitStatusLb.Content = "Split failed. Check PDF selected";
                    break;
            }

        }

        private void PagesBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            // If the box cannot be modified, ensure the labeling of status is correct
            if (FileBox.SelectedItems.Count == 0 || FileBox.SelectedItems.Count > 1)
            {
                CombineStatusLb.Content = "Awaiting Operation";
            }
            // If the page sequence is invalid, let user know
            else if (!handler.ValidPageEntry(PagesBox.Text))
            {
                filePathObjects[FileBox.SelectedIndex].Pages = "";
                CombineStatusLb.Content = "Invalid Page Sequence. Ex: 1-3";
            }
            // Proper conditions to modify and update object
            else if (FileBox.SelectedItems.Count > 0 && handler.ValidPageEntry(PagesBox.Text))
            {
                filePathObjects[FileBox.SelectedIndex].Pages = PagesBox.Text;
                CombineStatusLb.Content = "Awaiting Operation";
            }
            
           
              
        }

        private void ExitBtnMerge_Click(object sender, RoutedEventArgs e)
        {
            System.Environment.Exit(0);
        }
    }
}