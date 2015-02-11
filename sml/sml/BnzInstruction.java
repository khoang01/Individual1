package sml;


public class BnzInstruction extends Instruction {

	//private int result;
	private int op1;
	private String op2;
	
	public BnzInstruction(String label, String op) {
		super(label, op);
}
	
	public BnzInstruction(String label, int op1, String op2) {
		this(label, "bnz");
		this.op1 = op1;
		this.op2 = op2;
	}
	
	@Override
	public void execute(Machine m) {
		int value1 = m.getRegisters().getRegister(op1);
		String ins1 = op2;
		int ins2 = m.getLabels().indexOf(ins1);
		if (value1 > 0) {
			m.setPc(ins2);			
		}
		
		
    }
	

	
	@Override
	public String toString() {
		return super.toString() + " " + op1 + " to " + op2;
	}
}
