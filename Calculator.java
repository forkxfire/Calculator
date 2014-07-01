package testClasse;


import java.util.Random;



public class Calculator {

	private Player me;
	private Player dueler;
	private int iterations =50000;
	private static String fightType;

	public Calculator(int attLvlMe, int strLvlMe , int defLvlMe , int hpLvlMe , int attLvlDueler, int strLvlDueler , int defLvlDueler , int hpLvlDueler){


		me = new Player(attLvlMe,strLvlMe , defLvlMe ,  hpLvlMe);
		dueler = new Player(  attLvlDueler,  strLvlDueler ,  defLvlDueler ,  hpLvlDueler);


	}

	public int random(int min, int max){
		Random rand = new Random();


		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public float percentage() {
		// TODO Auto-generated method stub
		me.set_multiplier(1);
		dueler.set_multiplier(1);

		if(fightType.contains("dds")){
			if(fightType.contains("whip")){
				me.set_style("accurate");
				dueler.set_style("accurate");
			}
			else{ // dscim
				me.set_style("aggressive");
				dueler.set_style("aggressive");
			}
			me.set_bonuses(25, 40, 0);
			dueler.set_bonuses(25, 40, 0);
			me.set_multiplier(1.15f);
			dueler.set_multiplier(1.15f);
		}
		else if(fightType.equals("whip")){
			me.set_style("accurate");
			me.set_bonuses(82, 82, 0);
			dueler.set_style("accurate");
			dueler.set_bonuses(82, 82, 0);
		}
		else if(fightType.equals("dscim")){
			me.set_style("aggressive");
			me.set_bonuses(67, 66, 1);
			dueler.set_style("aggressive");
			dueler.set_bonuses(67, 66, 1);
		}
		else if(fightType.equals("rscim")){
			me.set_style("aggressive");
			me.set_bonuses(45, 44, 1);
			dueler.set_style("aggressive");
			dueler.set_bonuses(45, 44, 1);
		}
		else if(fightType.equals("tentacle")){
			me.set_style("accurate");
			dueler.set_style("accurate");
			me.set_bonuses(90, 86, 0);
			dueler.set_bonuses(90, 86, 0);
			me.set_multiplier(1);
			dueler.set_multiplier(1);
		}
		else{
			//boxing
			me.set_bonuses(0,0,0);
			dueler.set_bonuses(0,0,0);
			me.set_style("aggressive");
			dueler.set_style("aggressive");
		}

		me.max_rolls();
		dueler.max_rolls();



		float accuracyMe;
		if(me.max_attack_roll > dueler.max_defense_roll){
			accuracyMe=1-(dueler.max_defense_roll+1)/(2*me.max_attack_roll);
		}
		else{
			accuracyMe=(me.max_attack_roll-1)/(2*dueler.max_defense_roll);
		}

		float accuracyDueler;
		if(dueler.max_attack_roll > me.max_defense_roll){
			accuracyDueler=1-(me.max_defense_roll+1)/(2*dueler.max_attack_roll);
		}
		else{
			accuracyDueler=(dueler.max_attack_roll-1)/(2*me.max_defense_roll);
		}

		System.out.println(accuracyMe);

		System.out.println(accuracyDueler);



		//	float dpsMe = (float) (me.max_strength_roll/2.4);
		//	float dpsDueler = (float) (dueler.max_strength_roll/2.4);

		//	System.out.println(dpsMe);
		//	System.out.println(dpsDueler);



		boolean dead = false;

		//		System.out.println("me-"+me.max_attack_roll);
//		System.out.println("me-"+me.max_strength_roll);
		//		System.out.println("me-"+me.max_defense_roll);
		//		System.out.println("dueler-"+dueler.max_attack_roll);
//		System.out.println("dueler-"+dueler.max_strength_roll);
		//		System.out.println("dueler-"+dueler.max_defense_roll);

		for(int i = 0; i < iterations; i++){



			int whoIsFirst = random(0,1); 
			int attackNb = 0; 


			while(!dead){



				if(attackNb==4 && fightType.equals("ddsToDscim") ){
					me.set_bonuses(67, 66, 1);
					dueler.set_bonuses(67, 66, 1);
					me.set_multiplier(1);
					dueler.set_multiplier(1);

					me.max_rolls();
					dueler.max_rolls();



				}
				else if(attackNb==4 && fightType.equals("ddsToWhip")){
					me.set_bonuses(82, 82, 0);
					dueler.set_bonuses(82, 82, 0);
					me.set_multiplier(1);
					dueler.set_multiplier(1);

					me.max_rolls();
					dueler.max_rolls();





				}
				else if(attackNb==4 && fightType.equals("ddsToTentacle")){
					me.set_bonuses(90, 86, 0);
					dueler.set_bonuses(90, 86, 0);
					me.set_multiplier(1);
					dueler.set_multiplier(1);

					me.max_rolls();
					dueler.max_rolls();




				}

				me.roll();
				dueler.roll();
				
				// who hits first
				
				if(whoIsFirst==1){

					// chance to hit
					
						Random r = new Random();
						float chance = r.nextInt(100);
						
						
						if(chance<accuracyDueler*100){
							dueler.damage = (dueler.strength_roll);
							me.remaining_hitpoints -= dueler.damage;
						}

					

					if(me.remaining_hitpoints <= 0){
							dead = true;
							dueler.victories++;
							me.losses++;
					}
					whoIsFirst=0;
				
				}
				else{
					
					
						Random r = new Random();
						float chance = r.nextInt(100);
						
						// chance to hit
					
						if(chance<accuracyMe*100){
							me.damage = (me.strength_roll);
							dueler.remaining_hitpoints -= me.damage;
						}
					


					if(dueler.remaining_hitpoints <= 0){
							dead = true;
							dueler.losses++;
							me.victories++;
					}
					whoIsFirst=1;
				}
				attackNb++;

			}
			me.remaining_hitpoints = me.hitpoints;
			dueler.remaining_hitpoints = dueler.hitpoints;
			dead = false;
			
		}

		return (float) me.victories/iterations * 100;

	}

	private class Player{


		private int hitpoints, remaining_hitpoints;
		private int attack_level, strength_level, defense_level;
		private int attack_bonus, strength_bonus, defense_bonus;
		private int attack_style, strength_style, defense_style;
		private float multiplier;
		private float max_attack_roll;
		private float max_defense_roll;
		private double max_strength_roll;
		private int attack_roll, strength_roll, defense_roll;
		private int damage;
		private int victories = 0;
		private int losses=0;

		public Player(int attLvl, int strLvl , int defLvl , int hpLvl){

			attack_level = attLvl;
			strength_level = strLvl;
			defense_level = defLvl;
			hitpoints = hpLvl;
			remaining_hitpoints = hpLvl;

		}

		public int attack(int defense){
			if (attack_roll > defense){
				damage = strength_roll;
			}
			else{
				damage = 0;
			}
			return 0;
		}

		public  void set_bonuses(int attack, int strength, int defense){
			attack_bonus = attack;
			strength_bonus = strength;
			defense_bonus = defense;
		}


		public void set_multiplier(float mult){
			multiplier = mult;
		}


		public void set_style(String attackstyle){

			if(attackstyle == "accurate"){
				attack_style = 3;
				strength_style = 0;
				defense_style = 0;
			}
			if(attackstyle == "aggressive"){

				attack_style = 0;
				strength_style = 3;
				defense_style = 0;
			}
			if(attackstyle == "defensive"){
				attack_style = 0;
				strength_style = 0;
				defense_style = 3;
			}
			if(attackstyle == "controlled"){
				attack_style = 1;
				strength_style = 1;
				defense_style = 1;
			}

		}



		public void  max_rolls(){



			float ea =  attack_level + attack_style + 8;
			float mar = (float) Math.floor(ea * (1 + attack_bonus/64));


			max_attack_roll = mar/10;


			int cumulativeStr = (strength_level + strength_style);
			double msr= ((13 + (cumulativeStr) + (strength_bonus / 8) + ((cumulativeStr * strength_bonus) / 64)));
			msr /= 10;
			max_strength_roll = Math.floor(msr * multiplier);



			float ed = defense_level + defense_style + 8;
			float mdr = (float) Math.floor(ed * (1 + defense_bonus/64));
			max_defense_roll = mdr/10;


		}

		public void roll(){
			attack_roll = random(0,(int) max_attack_roll);
			strength_roll = random(0,(int) max_strength_roll);
			defense_roll = random(0,(int) max_defense_roll);

		}


	}

	public static void main(String[] args){



		//		Calculator instance = new Calculator(60,80,45,75,60,60,60,70);

		//		Calculator instance = new Calculator(60,87,45,79,60,78,56,76);
		//	
		//		Calculator instance = new Calculator(60,70,1,65,60,75,1,60);


		//		Calculator instance = new Calculator(99,99,91,99,90,99,95,99);

		Calculator.fightType="dscim";
		
		//fight type  = ddsToDscim , ddsToWhip , ddsToTentacle , dscim, whip, boxing ,rscim
		
		
		
		//		Calculator instance = new Calculator(62,77,2,68,60,81,1,71);


		//		Calculator instance = new Calculator(60,70,15,65,60,70,1,65);
		//		Calculator instance = new Calculator(62,77,2,68,60,81,1,71);

		Calculator instance = new Calculator(60,70,15,65,60,70,1,65);	


		System.out.println("");

		System.out.println("STATS att=" +instance.me.attack_level + " str="+instance.me.strength_level + " def="+instance.me.defense_level + " hp="+instance.me.hitpoints );
		System.out.println("STATS att=" +instance.dueler.attack_level + " str="+instance.dueler.strength_level + " def="+instance.dueler.defense_level+ " hp="+instance.dueler.hitpoints  );
		System.out.println("");

		System.out.println("NB OF VIRTUAL FIGHTS " + instance.iterations);



		System.out.println("PERCENTAGE = "  + instance.percentage() + " %");

		System.out.println(" TOTAL WINS = " + instance.me.victories);
		System.out.println(" TOTAL LOSSES = " + instance.me.losses);

	}




}
