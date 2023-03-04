package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile("betteraddproduct.in");
        StdOut.setFile("Output.in");
        Warehouse warehouse= new Warehouse();
        int input=StdIn.readInt();
        int i=0;
        while(StdIn.hasNextChar() && i<input){
            int day=StdIn.readInt(), id=StdIn.readInt();
            String name=StdIn.readString();
            int stock=StdIn.readInt(), demand=StdIn.readInt();
            warehouse.betterAddProduct(id, name, stock, day, demand);
            i++;
        }
        StdOut.println(warehouse);
    }
}
