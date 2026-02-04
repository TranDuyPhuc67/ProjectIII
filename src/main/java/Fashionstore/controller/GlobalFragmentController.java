package Fashionstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import Fashionstore.models.Item;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalFragmentController {
	@ModelAttribute("contact")
	public String loadInfor(Model model) {
		return "Tran Duy Phuc: 0367371943";
	}
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("cartitems")
	public List<Item> loadCartItem(Model model,HttpSession session) {
		List<Item> items=new ArrayList<Item>();
		if(session.getAttribute("cartitems")!=null)
		{
			items=(List<Item>)session.getAttribute("cartitems");
		}
		Double total=items.stream().mapToDouble(Item::getTotal).sum();
		model.addAttribute("totalCart",total);
		return items;
	}
}