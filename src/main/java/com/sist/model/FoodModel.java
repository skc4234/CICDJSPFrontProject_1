package com.sist.model;

import java.io.PrintWriter;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sist.vo.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;

/*
 *    브라우저 요청(*.do)
 *    	  | ==========> request
 *    Controller
 *    	  | ==========> request
 *    	Model
 *    	  | ==========> 매개변수
 *    	 DAO
 *    	  | ==========> 결과값
 *    	Model
 *    	  | ==========> request.setAttribute()
 *    Controller
 *    	  | ==========> request.setAttribute()
 *      브라우저
 *    
 *    
 *    
 *    
 */

@Controller // Model 클래스 => 위임자 => Spring
public class FoodModel {
	@RequestMapping("food/find.do") // GetMapping / PostMapping
	public String food_find(HttpServletRequest request, HttpServletResponse response) {
		/*
		String[] types=request.getParameterValues("type");
		String page=request.getParameter("page");
		if(page==null) page="1";
		String col=request.getParameter("column");
		if(col==null) col="address";
		String ss=request.getParameter("ss");
		if(ss==null) ss="마포";
		int curPage=Integer.parseInt(page);
		final int ROWSIZE=12;
		int start=(curPage*ROWSIZE)-ROWSIZE;
		
		Map map=new HashMap();
		map.put("fdArr", types);
		map.put("start", start);
		map.put("column", col);
		map.put("ss", ss);
		
		List<FoodVO> list=FoodDAO.foodFindData(map);
		int count=FoodDAO.foodFindCount(map);
		int totalPage=(int)(Math.ceil(count/(double)ROWSIZE));
		
		final int BLOCK=10;
		int startPage=((curPage-1)/BLOCK*BLOCK)+1;
		int endPage=((curPage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalPage) endPage=totalPage;
		
		
		request.setAttribute("list", list);
		request.setAttribute("curPage", curPage);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("count", count);
		request.setAttribute("ss", ss);
		// => 문제 발생
		// 	=>
		 */
		//request.setAttribute("main_jsp", "find.jsp");
		return "find.jsp";
	}
	
	@RequestMapping("food/find_ajax.do")
	public void food_find_ajax(HttpServletRequest request, HttpServletResponse response) {
		String[] types=request.getParameterValues("type");
		String page=request.getParameter("page");
		if(page==null) page="1";
		String col=request.getParameter("column");
		if(col==null) col="address";
		String ss=request.getParameter("ss");
		if(ss==null) ss="마포";
		int curPage=Integer.parseInt(page);
		final int ROWSIZE=12;
		int start=(curPage*ROWSIZE)-ROWSIZE;
		
		Map map=new HashMap();
		map.put("fdArr", types);
		map.put("start", start);
		map.put("column", col);
		map.put("ss", ss);
		
		List<FoodVO> list=FoodDAO.foodFindData(map);
		int count=FoodDAO.foodFindCount(map);
		int totalPage=(int)(Math.ceil(count/(double)ROWSIZE));
		
		final int BLOCK=10;
		int startPage=((curPage-1)/BLOCK*BLOCK)+1;
		int endPage=((curPage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalPage) endPage=totalPage;
		
		System.out.println(Arrays.toString(types));
		
		int i=0; 
		JSONArray arr=new JSONArray(); 
		for(FoodVO vo: list) {
			JSONObject obj=new JSONObject(); 
			obj.put("no", vo.getNo()); 
			obj.put("name",vo.getName()); 
			obj.put("poster", vo.getPoster()); 
			obj.put("address", vo.getAddress()); 
			obj.put("type", vo.getType()); 
			if(i==0) {
				obj.put("curPage", curPage); 
				obj.put("totalPage", totalPage);
				obj.put("startPage", startPage);
				obj.put("endPage", endPage);
				obj.put("count", count);
				obj.put("ss", ss);
			} 
			arr.add(obj);
			i++; 
		}
		
		try {
			// 일반 문자열, HTML 전송 => text/html
			// JSON 전송 => text/plain
			response.setContentType("text/plain;charset=UTF-8");
			PrintWriter out=response.getWriter(); 
			out.write(arr.toJSONString());
		} catch (Exception e) { 
			e.printStackTrace();
		}
		 
	}
}
