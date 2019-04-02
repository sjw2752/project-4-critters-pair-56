package assignment4;

public class MyCritter3 extends MyCritter1 {

	public String toString () {
		return "3";
	}
	
	private int myDir = 0;
	
	@Override
	public void doTimeStep () {
		walk(myDir);
		myDir = (myDir+1)%8; // change direction each walk call, CCW.
	}

}
