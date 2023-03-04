package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        int lastNum=id%10;
        Product prod1= new Product(id, name, stock, day, demand);
        
        sectors[lastNum].add(prod1);
    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {

        int lastNum=id%10;
        //use .swim() and getSize()
        //create new int
        int size=sectors[lastNum].getSize();
        sectors[lastNum].swim(size);
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
        int lastNum=id%10;
        Sector current= sectors[lastNum];
    //check for size of 5  
        if (current.getSize()==5){
    //evict
            current.swap(1, 5);
            current.deleteLast();
            current.sink(1);
        }
    }


  


    

    
    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        int token=id%10;
        int num=sectors[token].getSize();

        for(int i=1; i<=num;i++){

            if( sectors[token].get(i).getId()==id){

                sectors[token].get(i).updateStock(amount);
            }
        }
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        int token=id%10;
        int num=sectors[token].getSize();

    //loop based on size of num
    //use .getID() to check if ID is similar to the sector[token]
        for(int i=1;i<=num;i++){

            if(sectors[token].get(i).getId()==id){

                sectors[token].swap(i, num);
                sectors[token].deleteLast();
                sectors[token].sink(i);
                break;
            }
        }
    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        int token=id%10;
        int num=sectors[token].getSize();

            //loop based on num size
            //check based on token for sectors[]

        for(int i=1;i<=num;i++){

            if(sectors[token].get(i).getId()==id&&sectors[token].get(i).getStock()-amount>=0){

                sectors[token].get(i).setLastPurchaseDay(day);


                sectors[token].get(i).updateStock(-amount);
                sectors[token].get(i).updateDemand(amount);
                sectors[token].sink(i);
            }
        }
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        int num=id%10;
        //use addProduct() method
        //check sectors is less than 5
        if(sectors[num].getSize()<5){

            addProduct(id, name, stock, day, demand);
        }

        else {

            int num2=id+1;
            //while loop and use fixHeap() based on temp value
            while(num2%10!=num){

                if(sectors[num2%10].getSize()<5){

                    Product temp = new Product(id, name, stock, day, demand);
                    //sectors[num2%10].getsize()
                    sectors[num2%10].add(temp);
                    //USE FIXHEAP THEN RETURN
                    fixHeap(num2);
                    return;
                }
                
                num2++;
            }
            addProduct(id, name, stock, day, demand);
        }   
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}