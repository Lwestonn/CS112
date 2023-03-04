package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile("C:\Programs\cs112\RUWarehouse\RUWarehouse\deleteproduct.in");
        StdOut.setFile("C:\Programs\cs112\RUWarehouse\RUWarehouse\Output.in");
        Warehouse a = new Warehouse();
        int numb = StdIn.readInt();
    
        int day;
        int id;
        String name;
        int stock;
        int demand;
        for(int i = 0;i<numb;i++){
            if(StdIn.readString().equals("add")){
                day = StdIn.readInt();
                id=StdIn.readInt();
                name = StdIn.readString();
                id = StdIn.readInt();
                name = StdIn.readString();
                stock = StdIn.readInt();
                demand = StdIn.readInt();
                a.addProduct(id, name, stock, day, demand);
            }else{
                id=StdIn.readInt();
                a.deleteProduct(id);
            }
        }
        StdOut.println(a);
    }
    
}
