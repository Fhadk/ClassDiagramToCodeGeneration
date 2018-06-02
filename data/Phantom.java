//package Resources;

interface RMI {
	public double value(double count,String name);
	public void getValue(String nameType);
}
class Phantom {

	public int field;
	private int value;
	protected String name;
	private double count;
	
	public Phantom(){
		System.out.println("Phantom Class!!!\n");
	}
	
	private int draw(){
		return 1;
	}
	private String getName(){
		return "";
	}
	
	
	//public static void main(String[] args){
		//new Phantom();
		//new Vampire();
	//}
}


class Vampire {
	public String name;
	private int triangle;
	
	public Vampire(){
		System.out.println("Vampire Class!!!\n");
	}
	
	
	public void setName(String name){
		this.name=name;
	}
	
	
	private String getPerson(){
		return "";
	}
}