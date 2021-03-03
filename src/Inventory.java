import java.util.HashMap;

/**
 * A store's inventory
 * Uses two HashMaps store product quantity and information
 *
 * @author Stefan Lukic - 101156711, Filip Lukic - 101156713
 * @version 1.1
 */
public class Inventory {

    private HashMap<Integer, Integer> productQuantity; // hashmap mapping id to quantities
    private HashMap<Integer, Product> productInfo;     // hashmap mapping id to Product

    /**
     * Default Constructor for Inventory
     */
    public Inventory(){
        this(null, null);
    }

    /**
     * Init an inventory given an array of products and their corresponding quantities
     * @param products Product[], array of Products
     * @param quantities int[], array of Product stock
     */
    public Inventory(Product[] products, int[] quantities){
        productQuantity = new HashMap<>();
        productInfo = new HashMap<>();

        if (products !=null && quantities !=null && products.length == quantities.length) {
            for (int i=0; i<products.length; i++){
                productQuantity.put(products[i].getId(), quantities[i]);
                productInfo.put(products[i].getId(), products[i]);
            }
        }
    }

    /**
     * Get method for productQuantity HashMap
     *
     * @return HashMap<Integer, Integer>, hashmap mapping id to quantities
     */
    public HashMap<Integer, Integer> getProductQuantity() {
        return productQuantity;
    }

    /**
     * Get method for productInfo HashMap
     *
     * @return HashMap<Integer, Products>, hashmap mapping id to Products
     */
    public HashMap<Integer, Product> getProductInfo() {
        return productInfo;
    }

    /**
     * Get stock of given product id
     *
     * @param id Product ID
     * @return Returns quantity of product. Returns -1 if no id entry exists
     */
    public int getStock(int id) {
        return productQuantity.getOrDefault(id, -1); //return -1 if no id entry exists
    }

    /**
     * Add stock of a given product, new products are allowed
     *
     * @param myProduct Product, Product of which stock is to be added
     * @param amount    int, Amount of stock to add
     */
    public void addStock(Product myProduct, int amount) {
        if (productQuantity.get(myProduct.getId()) == null) { //product does not already exist
            productInfo.put(myProduct.getId(), myProduct);
        }
        productQuantity.put(myProduct.getId(), productQuantity.getOrDefault(myProduct.getId(), 0) + amount); //add amount
    }

    /**
     * Remove stock of a given product id.
     * If stock quantity becomes negative, set it to 0.
     *
     * @param id     int, ID of stock to remove
     * @param amount int, Amount of stock to remove
     * @param remIfZero boolean, remove the product if 0 stock or less (used for ShoppingCart)
     */
    public void removeStock(int id, int amount, boolean remIfZero) {
        if (productQuantity.get(id) != null) {
            if (productQuantity.get(id) - amount <= 0) {
                if (remIfZero){
                    removeProduct(id);
                } else{
                    productQuantity.put(id, 0);
                }
            } else {
                productQuantity.put(id, productQuantity.getOrDefault(id, 0) - amount);
            }
        }
    }

    /**
     * Removes (deletes) a Product from the inventory
     *
     * @param id int, Product id corresponding to product to be removed
     */
    public void removeProduct(int id){
        productQuantity.remove(id);
        productInfo.remove(id);
    }

    /**
     * Return information of a given product id
     *
     * @param id Product ID
     * @return Returns Product containing product information
     */
    public Product getInfo(int id) {
        return productInfo.get(id);
    }

}
