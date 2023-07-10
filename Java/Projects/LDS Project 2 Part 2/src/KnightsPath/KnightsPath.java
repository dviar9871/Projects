package KnightsPath;

public class KnightsPath
{
	protected Queue queue = new Queue();
	public KnightsPath(int sRow, int sCol, int fRow, int fCol) {
		Queue<Path> queue = new Queue<Path>();
		
		Position start = new Position(sRow, sCol);
		Position end = new Position(fRow, fCol);
		
		Path path = new Path(start);
		queue.add(path);
		
		
		Path currentPath;
		boolean hasPathsLeft=false;
		int currentRow=0;
		int currentColumn=0;
		
		while(!hasPathsLeft) {
			currentRow=queue.element().getPosition().getRow();
			currentColumn=queue.element().getPosition().getColumn();
			currentPath=queue.element();
			
			if(currentPath.getPosition().equals(end)) {
				hasPathsLeft=true;
				System.out.println(queue.poll());
			} 
			
			else if(((currentRow<0)|| currentRow>8)|| (currentColumn>8)||(currentColumn<0)) {
				queue.poll();
			}
			
			else {
				queue.add(currentPath.change(2,1));
				queue.add(currentPath.change(1,2));
				queue.add(currentPath.change(-1,2));
				queue.add(currentPath.change(-2,1));
				queue.add(currentPath.change(-2,-1));
				queue.add(currentPath.change(-1,-2));
				queue.add(currentPath.change(1,-2));
				queue.add(currentPath.change(2,-1));
				queue.poll();
			}
		}
		
		
		
		
	}
	
	
	
	
	
	
	
}
