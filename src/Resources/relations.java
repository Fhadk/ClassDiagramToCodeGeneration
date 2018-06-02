package Resources;

class Test {

	int field1;
	String field2;
	
	Test(int a, float b) {
		System.out.println("I am the Constructor");
	}
	
	int func(String c, double d) {
		return 1;
	}
}

abstract class abc {
    abstract void draw();
}

abstract class def extends abc {
    abstract void paint();
}

interface xyz {
    void sketch();
}

interface uvw extends xyz{
    void capture();
}

class draw extends def implements xyz {

	int field1;
	String field2;
        Test field3;
	
	draw(int a, float b, Test c) {
		System.out.println("I am the Constructor");
	}
	
	Test func2(String c, double d) {
		return null;
	}
        
        public void draw() {
            
        }
        
        public void paint() {
            
        }
        
        public void sketch() {
            
        }
}
