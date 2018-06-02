package Resources;

interface RMI {
	public int valueToCount(double count,String name);
	public void getValue(String nameType);
	public String getNameText();
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


class toDraw{
	public String name;
	private int triangle;
	
	public toDraw(){
		System.out.println("Vampire Class!!!\n");
	}
	
	
	public void setName(String name){
		this.name=name;
	}
	
	
	private String getPerson(){
		return "";
	}
}