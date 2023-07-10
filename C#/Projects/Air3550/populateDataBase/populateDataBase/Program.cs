

using ClosedXML.Excel;
using System;
using System.Security.Cryptography.X509Certificates;

class Program
{

    static void Main(String[] args)
    {
        var workbook = new XLWorkbook("C:\\Users\\dxvia\\Desktop\\Book1.xlsx"); // Open database
        var flightWorksheet = workbook.Worksheet("Sheet2"); // Get Flight Manifest sheet

        var flightTable = flightWorksheet.Tables.Table(0);

        System.DateTime sixMonthsLater = System.DateTime.Now.AddMonths(6);
        System.DateTime now = System.DateTime.Now;

       
        int i = 44;
        while (now < sixMonthsLater) { 
            flightTable.InsertRowsBelow(1);
            var lastRow = flightTable.DataRange.Row(i - 43);
            var row = flightTable.DataRange.Row(i);
            
            //Console.WriteLine("Working! i is: " + i);

            for (int j = 1; j <= 6; j++)
            {
                if (j == 1)
                {
                    int newVal = ((int)lastRow.Cell(j).Value) + 44;
                    row.Cell(j).Value = newVal;
                }
                else if(j == 2)
                {
                    row.Cell(j).Value = lastRow.Cell(j).Value;

                }
                else if(j == 3)
                {
                    row.Cell(j).Value = lastRow.Cell(j).Value;

                }
                else if (j == 4)
                {

                    System.DateTime departTime = System.DateTime.Parse(lastRow.Cell(j).Value.ToString());
                    departTime = departTime.AddDays(1);
                    row.Cell(j).Value = departTime.ToString();

                }
                else if (j == 5)
                {

                    System.DateTime departTime = System.DateTime.Parse(lastRow.Cell(j).Value.ToString());
                    departTime = departTime.AddDays(1);
                    row.Cell(j).Value = departTime.ToString();

                }
                else if (j == 6)
                {

                    row.Cell(j).Value = lastRow.Cell(j).Value;
                }
            }

            if(i % 44 == 0)
                now = now.AddDays(1);
            i++;
        }

        workbook.Save();


    }
}