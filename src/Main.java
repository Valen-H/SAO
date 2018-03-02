import java.io.*;
import java.util.*;
import java.util.regex.*;

class Enemy extends Main {
	int hp = (lvl * 100 + 100) / 20;
	int atk = lvl + 1;
	int def = lvl;
	String name = "monster";
	String[] monst = {"Midgardsormr","Ghoul","Salamander","Sylph","Undine","Kraken","Golem","Noob","Vampire","Little Girl","Daemon","Incubus","Succubus","Boar","Slime","Nepenthes","Ragout","Warg","Dionea","Feline","Spectre","PKer","Zaza","Dragon","Thor"};
	Enemy() {
		this.hp = rnd(hp, hp * 5);
		this.atk = rnd(atk, atk * 5);
		this.def = rnd(def, def + 2);
		this.name = rnd(monst);
	}
}//Enemy
public class Main {
	static String name = "Kirito";
	static int cred = 0;
	static int lvl = 0;
	static int exp = 0;
	static int hp = 100;
	static int atk = 5;
	static int def = 1;
	static boolean Adv = true;
	static Enemy ene;
	static String data = "Name:" + name + "\nMoney:" + cred + "\nLevel:" + lvl + "\nExperience:" + exp + "/0\nHealth:100/100\nAttack:5\nDefense:1\n";
	protected static int rnd(int frm, int to) {
		Random rand = new Random();
		return rand.nextInt(to - frm + 1) + frm;
	}//rnd
	protected static boolean rnd() {
		Random rand = new Random();
		return rand.nextBoolean();
	}//rnd
	protected static String rnd(String[] str) {
		Random rand = new Random();
		return str[rand.nextInt(str.length)];
	}//rnd
	protected static int fac(int fact) {
		return fact > 1 ?fact * fac(fact - 1): fact;
	}//fact
	protected static void init() throws IOException {
		FileWriter file = new FileWriter("/sdcard/AppProjects/SAO/data.txt");
		System.out.println("Input NickName: ");
		Scanner name = new Scanner(System.in);
		file.write("Name:" + name.next() + "\nMoney:0\nLevel:0\nExperience:0/0\nHealth:100/100\nAttack:5\nDefense:1\n");
		file.close();
		String[] args = {};
		main(args);
	}//init
	protected static void save() throws IOException {
		FileWriter file = new FileWriter("/sdcard/AppProjects/SAO/data.txt");
		file.write(data = "Name:" + name + "\nMoney:" + cred + "\nLevel:" + lvl + "\nExperience:" + exp + "/" + fac(lvl) + "\nHealth:" + hp + "/" + (lvl * 100 + 100) + "\nAttack:" + atk + "\nDefense:" + def + "\n");
		file.close();
	}//save
	protected static void adv() {
		if ((ene == null || ene.hp <= 0) && hp > 0) {
			if (rnd(0, 5) <= 3) {
				if (!Adv) {
					System.out.println("You decided to stay where you are and continue fighting monsters.");
					Adv = true;
				}
				ene = new Enemy();
				System.out.println("You encountered: " + ene.name + "\nHealth: " + ene.hp + "\nAttack: " + ene.atk + "\nDefense: " + ene.def + "\n");
			} else {
				System.out.println("\nAfter wandering around you find yourself lost.\nPick a path:\nNorth\nSouth\nEast\nWest\n");
				Adv = false;
			}
		} else if (hp > 0) {
			int log;
			ene.hp -= log = atk - ene.def > 0 ?atk - ene.def: 1;
			System.out.println("You attacked " + ene.name + " for " + log + " damage.\n(Health left: " + (ene.hp >= 0 ?ene.hp: 0) + " points)");
			hp -= log = ene.atk - def > 0 ?ene.atk - def: 1;
			hp = hp > 0 ?hp: 0;
			System.out.println(ene.name + " attacked you for " + log + " damage.\n(" + (hp > 0 ?hp: 0) + " life-points left.)");
			if (ene.hp <= 0) {
				System.out.println(ene.name + " died.");
				cred += log = (ene.hp + ene.atk * 2 + ene.def * 3) / 3;
				System.out.print(" (Earned: " + log + ", XP: " + (1 + (ene.atk + ene.def) / 10) + ")");
				exp += 1 + (ene.atk + ene.def) / 10;
				if (exp >= fac(lvl)) {
					System.out.println("Level Up! (Current Level: " + (++lvl) + ")");
					hp += hp / lvl;
					atk += 1 + 1 * rnd(0, 1) + 1 * rnd(0, 1) * rnd(0, 1);
					def += 1 + 1 * rnd(0, 1) * rnd(0, 1);
				}
			}
			if (hp <= 0) {
				System.out.println("You died...");
				ene = null;
				exp -= rnd(lvl / 2, lvl);
				System.out.println("Experience lost: " + lvl);
				if (exp < 0) {
					exp = 0;
				}
			} else if (hp <= (ene.atk - def) * 2) {
				System.out.println("Please Recover..");
			} else if (ene.hp <= 0) {
				ene = null;
			}
		} else {
			System.out.println("Please Respawn...");
		}
	}//adv
	public static void game() throws IOException {
		System.out.println("Action:");
		int log;
		Scanner sc = new Scanner(System.in);
		boolean un = false;
		String str;
		switch (str = sc.nextLine()) {
			case "Res":
			case "res":
				sc = new Scanner(System.in);
				System.out.println("Are you sure? (Reset)");
				if (!(str = sc.nextLine()).equalsIgnoreCase("yes")) {
					System.out.println("Aborted..");
					un = true;
					break;
				}
			case "Reset":
			case "reset":
				System.out.println("Account reseted...");
				init();
				return;
			case "Stat":
			case "stat":
			case "Status":
			case "status":
			case "info":
			case "Info":
			case "inf":
			case "Inf":
				System.out.println("\n" + data);
				break;
			case "Save":
			case "save":
			case "Sav":
			case "sav":
				save();
				System.out.println("Game Saved.");
				break;
			case "Rename":
			case "rename":
			case "Ren":
			case "ren":
				System.out.println("(Rename) Input your new NickName");
				sc = new Scanner(System.in);
				name = sc.nextLine();
				System.out.println("You are now called: " + name);
				break;
			case "Adventure":
			case "adventure":
			case "Adv":
			case "adv":
			case "Atk":
			case "atk":
				adv();
				break;
			case "Enemy":
			case "enemy":
			case "Ene":
			case "ene":
			case "Inf":
			case "inf":
				if (ene != null) {
					System.out.println("\nName:\n" + ene.name + "\nHealth:" + ene.hp + "\nAttack:" + ene.atk + "\nDefense:" + ene.def + "\n");
					break;
				} else {
					System.out.println("No encounters.");
				}
				break;
			case "Flee":
			case "flee":
			case "Esc":
			case "esc":
				if (rnd()) {
					hp -= log = ene.atk - def > 0 ?ene.atk - def: 1;
					System.out.println(ene.name + " attacked you for " + log + " damage while you were fleeing.\n(" + (hp > 0 ?hp: 0) + " life-points left.)");
				}
				if (rnd()) {
					ene = null;
					System.out.println("You flee...");
				} else {
					System.out.println("Escape thwarted.");
				}
				break;
			case "Respawn":
			case "respawn":
			case "res":
			case "Heal":
			case "heal":
			case "Recover":
			case "recover":
			case "Rec":
			case "rec":
				{
					if (cred >= lvl * 6 + 1) {
						log = ene == null ?lvl * 100 + 100: (hp + (lvl * 100 + 100) / 2 <= lvl * 100 + 100 ?hp + (lvl * 100 + 100) / 2: lvl * 100 + 100);
						save();
						if (hp <= 0) {
							hp = log;
							save();
							System.out.println("Resurrected...\n\n" + data);
							ene = null;
						} else if (hp > (lvl * 100 + 100) / 2) {
							System.out.println("Are you sure? (HP already high -> " + hp + "/" + (lvl * 100 + 100) + ")");
							sc = new Scanner(System.in);
							if ((str = sc.nextLine()).equalsIgnoreCase("yes")) {
								hp = log;
								save();
								System.out.println("Healed...\n" + data);
							} else {
								System.out.println("Aborted..");
								un = true;
								break;
							}//if max?
						} else {
							hp = log;
							save();
							System.out.println("Healed... (" + hp + "/" + (lvl * 100 + 100) + ")");
						}
						cred -= log = rnd(2 * lvl + 1, lvl * 6 + 1);
						if (cred < 0) {
							cred = 0;
							break;
						}
						System.out.println("-" + log + " credits..");
						break;
					} else {
						System.out.println("Not enough money... (" + cred + " owned / " + (lvl * 3) + " (at least) required)");
						System.out.println("Reset?");
						sc = new Scanner(System.in);
						if ((str = sc.nextLine()).equalsIgnoreCase("yes")) {
							init();
							String[] args = {};
							main(args);
							return;
						}
						break;
					}
				}
			case "Beg":
			case "beg":
				cred += log = rnd(1, lvl);
				System.out.println("You begged for: " + log + " credits. (Money: " + cred + ")");
				break;
			case "1":
			case "2":
			case "3":
			case "4":
			case "South":
			case "south":
			case "North":
			case "north":
			case "right":
			case "Right":
			case "East":
			case "east":
			case "left":
			case "Left":
			case "West":
			case "west":
				if (rnd(0, 2) == 0 && !Adv) {
					System.out.println("You went " + str + "...");
					cred += log = rnd(-lvl, lvl * 3);
					if (log > 0) {
						System.out.println("You found: " + log + " gold!");
					} else if (log < 0) {
						System.out.println("Bandits attacked you: " + -log + " gold lost... ~_~");
					} else {
						System.out.println("Bandits attacked.\nYou showed them a good lesson! ^_^");
					}
					break;
				} else if (ene == null) {
					System.out.println("You went " + str + "...");
					Adv = true;
					adv();
				} else {
					System.out.println("Even a fool knows you can't escape like this... ~_~");
				}
				break;
			case "Kill":
			case "kill":
				while (ene == null) {
					adv();
				}
				int rep = ene.hp / (atk - ene.def > 0 ?atk - ene.def: 1);
				while (rep-- != 0) {
					adv();
				}
				if (ene != null) {
					adv();
				}
				break;
			case "Exit":
				save();
			case "exit":
				System.exit(1);
				break;
			default:
				un = true;
		}
		if (un) {
			Pattern Cm = Pattern.compile("^([^ ]+) *([^ ]+)(?=(\\n|\\s|$))");
			Matcher cm = Cm.matcher(str);
			if (cm.find()) {
				if (cm.group(1).equalsIgnoreCase("adv")) {
					int rep = Integer.parseInt("0" + cm.group(2));
					if (rep <= 0) {
						ene = null;
						rep = 1;
					}
					while (rep-- != 0) {
						adv();
					}
				} else if (cm.group(1).equalsIgnoreCase("Ren") || cm.group(1).equalsIgnoreCase("Rename")) {
					name = cm.group(2) != null ?cm.group(2): "Kirito";
					System.out.println("You are now called: " + name);
				} else if (cm.group(1).equalsIgnoreCase("kill")) {
					int reps = Integer.parseInt("0" + cm.group(2));
					while (reps-- != 0) {
						while (ene == null) {
							adv();
						}
						int rep = ene.hp / (atk - ene.def > 0 ?atk - ene.def: 1);
						while (rep-- != 0) {
							adv();
						}
						if (ene != null) {
							adv();
						}
					}
				} else if (cm.group(1).equalsIgnoreCase("beg")) {
					int rep = Integer.parseInt("0" + cm.group(2));
					int Log = 0;
					log = rep * 2;
					if (log < hp) {
						hp -= log;
						while (rep-- != 0) {
							Log += rnd(1, lvl);
						}
						cred += Log;
						System.out.println("You begged for: " + Log + " credits. (Money: " + cred + ", Health: " + hp + " pts)");
					} else {
						System.out.println("This would kill you!... °_°");
					}
				}
			}
		}
		save();
		game();
	}//game
	public static void main(String[] args) throws IOException {
		System.out.println("Link Start!");
		File fil = new File("/sdcard/AppProjects/SAO/data.txt");
		fil.getParentFile().mkdirs();
		fil.createNewFile();
		FileReader file = new FileReader("/sdcard/AppProjects/SAO/data.txt");
		int c;
		data = "";
		int inc = 0;
		while ((c = file.read()) != -1) {
			inc++;
            data += ((char)c);
		}
		file.close();
		Pattern[] pat = {Pattern.compile("(?<=Name:)(.+)(?=(\\n|$))"),Pattern.compile("(?<=Money:)(\\d+)"),Pattern.compile("(?<=Level:)(\\d+)"),Pattern.compile("(?<=Experience:)(\\d+)"),Pattern.compile("(?<=Health:)(\\d+)"),Pattern.compile("(?<=Attack:)(\\d+)"),Pattern.compile("(?<=Defense:)(\\d+)")};
		if (inc == 0) {
			init();
			return;
		}
		Matcher[] mat = new Matcher[pat.length];
		for (int stp = 0; stp < pat.length; stp++) {
			mat[stp] = pat[stp].matcher(data);
			mat[stp].find();
		}
		System.out.println("Welcome " + (name = mat[0].group(0)) + "!");
		cred = Integer.parseInt(mat[1].group(0));
		lvl = Integer.parseInt(mat[2].group(0));
		exp = Integer.parseInt(mat[3].group(0));
		hp = Integer.parseInt(mat[4].group(0));
		atk = Integer.parseInt(mat[5].group(0));
		def = Integer.parseInt(mat[6].group(0));
		game();
	}//main
}//Main
