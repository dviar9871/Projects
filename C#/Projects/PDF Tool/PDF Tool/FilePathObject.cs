using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PDF_Tool
{
    internal class FilePathObject 
    {
        public string Path { get; set; }
        public string Pages { get; set; }
        public FilePathObject(string path, string pages)
        {
            Path = path;
            Pages = pages;
        }

        public FilePathObject(string path)
        {
            Path = path;
            Pages = string.Empty;
        }
        public override string ToString()
        {
            return string.Join(",", Path, Pages);
        }
    }

}
