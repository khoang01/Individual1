package sml;

public class OutInstruction extends Instruction{
    
	//private int result;
	private int op1;
	
	
	public OutInstruction(String label, String op) {
		super(label, op);
	}

	public OutInstruction(String label, int op1) {
		this(label, "out");
		//this.result = result;
		this.op1 = op1;
    }  
	
	@Override
	public void execute(Machine m) {
		int value1 = m.getRegisters().getRegister(op1);
		System.out.println("The contents of register" + " " + op1 + " is: " + value1);
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + op1;
	}
	
}