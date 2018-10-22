package org.elsys;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.coyote.Response;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/Servlet/*")
public class RestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HashMap<String, String> map;
	
    public RestServlet() {
        map = new HashMap<String, String>();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void getMap(){
    	int i = 1;
    	for(Entry<String, String> entry : map.entrySet()){
    		System.out.print(i + " \"" +  entry.getKey() + "\"" + " : ");
    		System.out.println("\"" +  entry.getValue() + "\"");
    		i++;
    	}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo().substring(1); //pathInfo = KEY
//		response.getWriter().append(System.lineSeparator() + System.lineSeparator() + "PathInfo: " + pathInfo + System.lineSeparator());
		
		if(!map.containsKey(pathInfo)){
			response.sendError(404);
		}
		else{
				JSONObject jsonResponse = new JSONObject();
				
				try {
					jsonResponse.put("value", map.get(pathInfo));
					jsonResponse.put("key", pathInfo);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().append(System.lineSeparator() + jsonResponse.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String line = reader.readLine();
		
		try {
			
			JSONObject json = new JSONObject(line);
			map.put(json.get("key").toString(), json.get("value").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo().substring(1);
		if(!map.containsKey(pathInfo)){
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		else{
			BufferedReader reader = request.getReader();
			String line = reader.readLine();
			
			try {
				JSONObject jsonData = new JSONObject(line);
				map.put(pathInfo, jsonData.get("value").toString());
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo().substring(1);
		if(map.containsKey(pathInfo)){
			map.remove(pathInfo);
		}
		else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
