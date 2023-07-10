package KnightsPath;

public class Position {
		private int row;
		private int column;
		
		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}

		public Position(int row, int column) {
			this.row=row;
			this.column=column;
		}
		
		public boolean equals(Position pos) {
			return((this.row==pos.getRow())&&(this.column==pos.getColumn()));
		}
		
		public String toString() {
			String string= "["+row+","+column+"]";
			return string;
		}
}
