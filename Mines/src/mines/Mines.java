package mines;

import java.util.Random;

public class Mines {
	private Mine[][] mines;
	private int height;
	private int width;
	private boolean showAll;

	public Mines(int height, int width, int numMines) {
		this.showAll = false;
		this.height = height;
		this.width = width;
		this.mines = new Mine[height][width];
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				this.mines[i][j] = new Mine();
			}
		}
		for (int i = 0; i < numMines; i++) {
			Random r = new Random();
			addMine(r.nextInt(this.height), r.nextInt(this.width));
		}
	}

	public boolean addMine(int i, int j) {
		if (!this.mines[i][j].getMine()) {
			this.mines[i][j] = new Mine(true);
			return true;
		}
		Random r = new Random();
		addMine(r.nextInt(this.height), r.nextInt(this.width));
		return false;
	}

	public boolean open(int i, int j) {
		int count = 0;
		if (isBoom(i, j) == 1) {
			return false;
		}
		if (isChecked(i, j)) {
			count += isBoom(i, j - 1);
			count += isBoom(i - 1, j - 1);
			count += isBoom(i - 1, j);
			count += isBoom(i + 1, j - 1);
			count += isBoom(i + 1, j);
			count += isBoom(i + 1, j + 1);
			count += isBoom(i, j + 1);
			count += isBoom(i - 1, j + 1);
			if (count > 0) {
				mines[i][j].setView((char) (count + 48));
				return true;
			} else {
				mines[i][j].setView(' ');
				open(i, j - 1);
				open(i - 1, j - 1);
				open(i - 1, j);
				open(i + 1, j - 1);
				open(i + 1, j);
				open(i + 1, j + 1);
				open(i, j + 1);
				open(i - 1, j + 1);
			}
		}
		return true;
	}

	public boolean isChecked(int i, int j) {
		try {
			if (mines[i][j].getView() != '.' || mines[i][j].getMine() == true) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	private int isBoom(int i, int j) {
		try {
			if (this.mines[i][j].getMine()) {
				return 1;
			} else
				return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public Mine[][] getMines() {
		return this.mines;
	}

	public void toggleFlag(int x, int y) {

		if (mines[x][y].getView() == '.') {
			mines[x][y].setView('F');
		} else if (mines[x][y].getView() == 'F') {
			mines[x][y].setView('.');
		}
	}

	public boolean isDone() {
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				if ((mines[i][j].getView() == '.' && mines[i][j].getMine() == false )||(mines[i][j].getView() == 'F' && mines[i][j].getMine() == false )) {
					return false;
				}
			}
		}
		return true;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public String get(int i, int j) {
		if (showAll == false) {
			if (mines[i][j].getView() == 'F') {
				return "F";
			} else if (mines[i][j].getMine() == true) {
				return ".";
			} else
				return String.valueOf(mines[i][j].getView());
		} else {
			if (mines[i][j].getView() == 'F') {
				return "F";
			} else if (mines[i][j].getMine() == true) {
				return "X";
			} else if (mines[i][j].getView() == '.') {
				int count = 0;
				count += isBoom(i, j - 1);
				count += isBoom(i - 1, j - 1);
				count += isBoom(i - 1, j);
				count += isBoom(i + 1, j - 1);
				count += isBoom(i + 1, j);
				count += isBoom(i + 1, j + 1);
				count += isBoom(i, j + 1);
				count += isBoom(i - 1, j + 1);
				if (count > 0) {
					return Integer.toString(count);

				} else {
					return " ";
				}
			} else
				return String.valueOf(mines[i][j].getView());
		}
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
					str += get(i, j);
			}
			str += "\n";
		}
		return str;
	}

}