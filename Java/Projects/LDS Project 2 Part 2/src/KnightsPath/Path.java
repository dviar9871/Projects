package KnightsPath;

import java.util.ArrayList;

public class Path {
	private int currentRow;
	private int currentColumn;
	
	protected ArrayList<Position> path;
	
	public Path(Position pos, ArrayList<Position> path) {
		currentRow=pos.getRow();
		currentColumn=pos.getColumn();
		
		ArrayList<Position> newList= new ArrayList<Position>();
		for(Position e: path) {
		newList.add(new Position(e.getRow(), e.getColumn()));
		}
		newList.add(new Position(currentRow, currentColumn));
		this.path= newList;
	}
	
	public Path(Position pos) {
		currentRow=pos.getRow();
		currentColumn=pos.getColumn();
		path= new ArrayList<Position>();
		path.add(pos);
	}
	
	public Path change(int rowDiff, int columnDiff) {
		
		ArrayList<Position> newList= new ArrayList<Position>();
		for(Position runner: path) {
		newList.add(new Position(runner.getRow(), runner.getColumn()));
		}
		
		Path newPath= new Path(new Position(currentRow+rowDiff, currentColumn+columnDiff), newList);
		return newPath;
	}
	
	public String toString() {
		String string="";
		
		for(Position p: path ) {
			string+=p+" ";
		}
		
		return string;
	}
	
	public Position getPosition() {
		return new Position(currentRow, currentColumn);
	}
}



