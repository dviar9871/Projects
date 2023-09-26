/* Author: Daniel Viar
 * Professor: Dr. Heuring
 * EECS 4590 - Algorithms
 * 
 * Project Overview: This project focuses on building a set of 5 letter words from a given directory of files with the option of choosing one of two data structures to hold the words.
 * After a print out of the list of words, the performance of this process is then provided.
 */
using Microsoft.Win32.SafeHandles;
using System.Diagnostics;
using System.Text;

// Trie data structure 
public class trie
{
    // Create nodes class for trie
    public class trieNode
    {
        List<trieNode> children;
        string value;
        short completeWord = 0;
     
        // Root Node Constructor
        public trieNode()
        {
            value = "";
            children = new List<trieNode>();
        }
        // Other Nodes Constructor
        public trieNode(string value)
        {
            this.value = value;
            children = new List<trieNode>();

        }
        // Getter for children
        public List<trieNode> Children { get { return children; } }
        // Setter/getter for Value
        public string Value
        {
            get { return value; }
            set { this.value = value; }
        }

        public short CompleteWord { get { return completeWord; } set { completeWord = value; } }

    }

    static trieNode root;
    // Constructor
    public trie()
    {
        root = new trieNode();

    }

    public trieNode Root { get { return root; } }

    // Add a word to the trie
    public void addWord(string word)
    {
        // Format word and set root
        trieNode curNode = root;
        word = word.ToUpper();
        // If trie is empty add to the root
        if (curNode.Children.Count == 0)
        {
            // Add a node for each letter
            for (int i = 0; i < word.Length; i++)
            {
                trieNode newNode = new trieNode();
                newNode.Value = word[i].ToString();
                curNode.Children.Add(newNode);

                curNode = newNode;

            }
            curNode.CompleteWord = 1;
            return;
        }
        // Use wordIndex to keep track of index in the original word 
        short wordIndex = 0;
        // Iterate through node's chuldren
        for (int i = 0; i < curNode.Children.Count; i++)
        {
            // If child contains the first letter of the word to be added
            if (curNode.Children[i].Value.Equals(word[wordIndex].ToString()))
            {
                // Move to child node that contains the letter
                curNode = curNode.Children[i];
                // Restart for loop
                i = -1;
                // Look for next letter in the word
                wordIndex++;
                // If word is in the trie
                if (wordIndex >= word.Length)
                    return;
            }
        }
        // If the current letter is not in the trie, add new nodes until the word is complete
        while (wordIndex < word.Length)
        {
            trieNode newNode = new trieNode(word[wordIndex].ToString());
            curNode.Children.Add(newNode);
            wordIndex++;
            curNode = newNode;

        }
        curNode.CompleteWord = 1;

        return;
    }

    // Find a word in the trie
    public bool findWord(string word)
    {
        // Set root
        trieNode curNode = root;
        word = word.ToUpper();
        // Use wordIndex to keep track of index in the original word 
        short wordIndex = 0;
        for (int i = 0; i < curNode.Children.Count; i++)
        {
            // If child contains the first letter of the word to be added
            if (curNode.Children[i].Value == word[wordIndex].ToString())
            {
                // Move to child node that contains the letter
                curNode = curNode.Children[i];
                // Restart for loop
                i = -1;
                // Look for next letter in the word
                wordIndex++;
                // If word is in the trie
                if (wordIndex >= word.Length && curNode.CompleteWord == 1)
                {

                    return true;
                }
            }
        }

        return false;

    }

    // Display Contents of trie
    public void printTrie(trieNode node, char[] curString, int level)
    {
        // Print all completed words in the trie
        if (node.CompleteWord != 0)
        {
            for (int k = level; k < curString.Length; k++)
            {
                curString[k] = '\0';
            }
            Console.WriteLine(curString);
        }

        // 
        // Go through each child node to recurse through
        for (int i = 0; i < node.Children.Count; i++)
        {
            if (node.Children[i] != null)
            {
                // get level of node
                curString[level] = node.Children[i].Value[0];
                printTrie(node.Children[i], curString, level + 1);
            }
        }
    }
}


class Program
{
    static void Main(String[] args)
    {
        // Parse args to ensure that only correct inputs are allowed through
        string setting = "";
        string directoryString = "";

        if (args.Length != 2)
        {
            Console.WriteLine("Invalid number of parameters");
                return;
        }

        foreach(var arg in args)
        {
            if(arg.Equals("-d") || arg.Equals("-D") || arg.Equals("-t") || arg.Equals("-T"))
            {
                setting = arg.ToLower();
            }
            else
            {
                directoryString = arg;
            }

        }
        if(!(setting.Equals("-d") || setting.Equals("-t")))
        {
            Console.WriteLine("Invalid Setting");
            return;
        }

     
        // Declare data structures being used
        trie trie = new trie();
        Dictionary<int, List<string>> dict =  new Dictionary<int,List< string>>();
        var directory = new System.IO.DirectoryInfo(directoryString);
        //var directory = new System.IO.DirectoryInfo("C:\\Users\\dxvia\\Desktop\\Test Files");

        // Start timing
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.Start();
        // Loop through all files in the directory
        try
        {
            foreach (var file in directory.GetFiles())
            {

                Console.WriteLine("Current File: " + file.Name);
                // Open StreamReader to look through text files
                var sr = new StreamReader(file.FullName);

                string curLine;
                // Read until there is nothing left
                while ((curLine = sr.ReadLine()) != null)
                {

                    // Break up line into words
                    var wordList = curLine.Split(' ');
                    // Go through each word in the line
                    foreach (string curWord in wordList)
                    {
                        // Only accept words that are 5 characters long
                        if (curWord.Length == 5)
                        {

                            bool punctFlag = false;
                            // get rid of any words that have punctuation
                            foreach (char letter in curWord)
                            {
                                if (!((letter >= 65 && letter <= 90) || (letter > 96 && letter <= 122)))
                                {
                                    punctFlag = true;
                                    break;
                                }
                            }
                            if (punctFlag)
                                continue;
                            // Add to Selected data structure
                            if (setting.Equals("-d")) // Dictionary
                            {
                                string curWordUpper = curWord.ToUpper();
                                bool inDict = false;
                                // Hashing function
                                int key = ((curWordUpper[0] * 70) + (curWordUpper[1] * 60) + (curWordUpper[2] * 50) + (curWordUpper[3] * 40)) % 104;
                                
                                // Chain if the key is already in Dictionary
                                if (dict.ContainsKey(key))
                                {
                                    foreach (var word in dict[key])
                                    {
                                        // Set flag if word is already in dictionary
                                        if (word.Equals(curWordUpper))
                                        {
                                            inDict = true;
                                            break;
                                        }
                                    }
                                    // word is not in dictionary so it is added to chain
                                    if (!inDict)
                                    {
                                        dict[key].Add(curWordUpper);
                                    }
                                }
                                else
                                    // Add new chain to dictionary
                                    dict.Add(key, new List<string>());



                            }

                           
                            else if (setting.Equals("-t")) // Trie
                            {
                                trie.addWord(curWord);
                            }
                        }
                    }
                }

            }
            // Stop timing and show time
            stopwatch.Stop();

            
            // Print out words
            if (setting.Equals("-t"))
            {
                char[] str = new char[5];
                trie.printTrie(trie.Root, str, 0);
            }
            else if (setting.Equals("-d"))
            {
                foreach (var chain in dict)
                {

                    foreach (var word in chain.Value)
                    {
                        Console.Write("{0}, ", word.ToString());
                    }
                    Console.WriteLine("\n");
                }
            }


            Console.WriteLine("\nTime Passed: {0}", stopwatch.Elapsed.ToString());
            Console.WriteLine("Printing not included in timing");



        }
        catch (DirectoryNotFoundException)
        {
            Console.WriteLine("Invalid directory entered");
        }

    }
}






