package metier;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
	//private List<DataInfo> foods;
	private List<Food> foods;
	private int nbFood = 20;
	
	public GameManager()
	{
		foods = new ArrayList<>();
		for(int i = 0;i<nbFood;i++)
		{
			foods.add(new Food((Math.random()*650)+50,(Math.random() * 700)+50));
		}
		
	}
	
	public List<DataInfo> GetFoods()
	{
		List<DataInfo> availableFood = new ArrayList<>();
		for(Food f:foods)
		{
			if(f.isAlive())
				availableFood.add(f.getPosition());
		}
		return availableFood;
	}
	
	public void RemoveFood(List<DataInfo> di)
	{
		for(DataInfo d:di)
		{
			for(Food f:foods)
			{
				if(d == f.getPosition())
				{
					f.DisableFood();
					break;
				}
			}
		}
	}
	

}
