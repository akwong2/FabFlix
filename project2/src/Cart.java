

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public class hashCart{
    		HashMap<String, Integer> cartItems;
    		public hashCart() {
    			cartItems = new HashMap<>();
    		}
    		public HashMap getCartItems() {
    			return cartItems;
    		}
    		
    		public void addToCart(String itemId, int num) {
    			if (!cartItems.containsKey(itemId))
    				cartItems.put(itemId, num);
    			else
    				cartItems.put(itemId, cartItems.get(itemId)+num);
    		}
    		
    		public void subToCart(String itemId, int num) {
    			cartItems.put(itemId, cartItems.get(itemId)-num);
    			if (cartItems.get(itemId) == 0) {
    				cartItems.remove(itemId);
    			}
    		}
    		
    		public void clearMap() {
    			cartItems.clear();
    		}
    		
    		public void clearItem(String itemId) {
    			cartItems.remove(itemId);
    		}
    		
    		public JsonArray cartArray() {
    			JsonArray jsonArray = new JsonArray();
    			
    			for (String key: cartItems.keySet())
    			{
    				JsonObject responseJsonObject = new JsonObject();
    				responseJsonObject.addProperty("title", key);
    				int value = cartItems.get(key);
    				responseJsonObject.addProperty("quantity", value);
    				jsonArray.add(responseJsonObject);
    				
    			}
    			return jsonArray;
    		}

    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
	
		hashCart shoppingCart;
		shoppingCart = (hashCart) request.getSession().getAttribute("cart");
		if (shoppingCart == null) {
			shoppingCart = new hashCart();
			request.getSession().setAttribute("cart", shoppingCart);
		}
		
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		if (id!=null) {
			if (type.equals("add")) {
				shoppingCart.addToCart(id, 1);
			}else if (type.equals("sub")){
				shoppingCart.subToCart(id, 1);
			} else if (type.equals("all")) {
				shoppingCart.clearItem(id);
			} else if(type.equals("rmall")) {
				shoppingCart.clearMap();
			}
			request.getSession().setAttribute("cart", shoppingCart);
			JsonArray jsonArray = shoppingCart.cartArray();
			out.write(jsonArray.toString());
			System.out.println(shoppingCart.getCartItems());
		}else {
			//out.write(request.getSession().getAttribute("cart").toString());
			
			JsonArray jsonArray = shoppingCart.cartArray();
			request.getSession().setAttribute("cart", shoppingCart);
			out.write(jsonArray.toString());
			
			
		}
		//shoppingCart.clearMap();
		//System.out.println(shoppingCart.getCartItems());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
