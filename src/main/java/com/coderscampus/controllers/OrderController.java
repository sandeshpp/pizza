package com.coderscampus.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coderscampus.domain.Customer;
import com.coderscampus.domain.Order;
import com.coderscampus.repository.OrderRepository;

@Controller
@RequestMapping("/orders")
public class OrderController
{
  private OrderRepository orderRepo;
  
  @RequestMapping(value="", method=RequestMethod.GET)
  public String orderGet (ModelMap model)
  {
    Order order = new Order();
    
    model.put("order", order);
    
    return "orders";
  }
  
  @RequestMapping(value="{orderId}", method=RequestMethod.GET)
  public String orderGet (@PathVariable Long orderId, ModelMap model)
  {
    Order order = orderRepo.findOne(orderId);
    
    model.put("order", order);
    
    return "orders";
  }
  
  @RequestMapping(value="{orderId}", method=RequestMethod.POST)
  public String orderPost (@PathVariable Long orderId, HttpServletRequest request, @ModelAttribute Order order, ModelMap model)
  {
    return "redirect:/orders/"+orderId+"/pizzas";
  }
  
  @RequestMapping(value="", method=RequestMethod.POST)
  public String orderPost (HttpServletRequest request, @ModelAttribute Order order, ModelMap model)
  {
    Customer customer = (Customer)request.getSession().getAttribute("customer");
    order.setCustomer(customer);

    Order savedOrder = orderRepo.save(order);
    
    return "redirect:/orders/"+savedOrder.getId()+"/pizzas";
  }

  @Autowired
  public void setOrderRepo(OrderRepository orderRepo)
  {
    this.orderRepo = orderRepo;
  }
  
}