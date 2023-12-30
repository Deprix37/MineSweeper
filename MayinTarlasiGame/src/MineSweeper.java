import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    // programım boyunca kullandıpım niteliklerimi tanımladığım yer
    int row;
    int col;
    String[][] mapWithMine;
    String[][] map;
    int userSelectRow;
    int userSelectCol;
    int minePrice = 0;
    Scanner input = new Scanner(System.in);

    boolean isGameOver = false;

    void launch() { // Kullanıcının oyunu yükleme aşamasında ilk karşılaştığı metot
        this.row = row;
        this.col = col;
        this.minePrice = (row * col) / 4;

        System.out.println("Mayın Tarlası Oyununa Hoş Geldiniz...");
        System.out.println("Oyununuzun matrisini belirlemelisiniz!!");
        controlMatrixSize();  // burada fonksiyonumı çağırıyorum
    }

    void createMatrix() {    // haritamı oluşturduğum fonksiyonum ayrıca mapimin ilk hali
        mapWithMine = new String[row][col];
        map = new String[row][col];
        for (int i = 0; i < mapWithMine.length; i++) {

            for (int j = 0; j < mapWithMine[i].length; j++) {
                mapWithMine[i][j] = "-";
                map[i][j] = "-";
            }
        }
    }

    void createMine() {   //random sınıfı ile rasgele sayılar oluşturup mayınlarımı yerleştirdiğim yer
        Random random = new Random();
        int maxMine = (this.col * this.row) / 4;

        for (int i = 0; i < maxMine; i++) {
            int a = random.nextInt(this.col * this.row);
            int rowindex = a / this.col;
            int colindex = a % this.col;
            if (mapWithMine[rowindex][colindex].equals("*")) {
                i--;
            }
            mapWithMine[rowindex][colindex] = "*";

        }
        renderMatrix(mapWithMine);

        System.out.println("================");

    }

    void run() {  //oyunu oynattığım fonksiyonum. Kullanıcıyla devamlı etkileşime girip oyundan ilerlemesini sağladığım bölüm
        this.userSelectRow = userSelectRow;
        this.userSelectCol = userSelectCol;
        renderMatrix(map);
        // döngüm oyun bitmedikce isgameover boolen değerim false döndükçe aşağıda ki kod bloklarım çalışır
        do {

            System.out.print("Satır giriniz: ");
            userSelectRow = input.nextInt();
            System.out.print("Sütun giriniz: ");
            userSelectCol = input.nextInt();
            if (userSelectRow < 0 || userSelectRow >= mapWithMine.length || userSelectCol >= mapWithMine[userSelectRow].length || userSelectCol < 0) {
                System.out.println("Hatalı konum girdiniz");
                isGameOver = false;
                continue;
            }

            if (mapWithMine[userSelectRow][userSelectCol] != "-" && mapWithMine[userSelectRow][userSelectCol] != "*") {
                System.out.println("Daha önce girdiğiniz lokasyonu tercih edemezsiniz.");
                isGameOver = false;
            }
            if (mapWithMine[userSelectRow][userSelectCol].equals("*")) {
                System.out.println("Game over!");
                isGameOver = true;
            }


            if (mapWithMine[userSelectRow][userSelectCol].equals("-")) {
                int mineCount = calculateMineCount(userSelectRow, userSelectCol);
                map[userSelectRow][userSelectCol] = Integer.toString(mineCount);
                mapWithMine[userSelectRow][userSelectCol] = Integer.toString(mineCount);
                renderMatrix(map);

                if (isWin(mapWithMine)) {
                    break;
                }
            }

        } while (!isGameOver);

    }

    private boolean isWin(String[][] mapWithMine) { //Kullanıcının oyunu kazandığını kontrol eden metodum
        for (int i = 0; i < mapWithMine.length; i++) {

            for (int j = 0; j < mapWithMine[i].length; j++) {
                if (mapWithMine[i][j].equals("-")) {
                    return false;
                }

            }


        }
        System.out.println("Tebrikler oyunu kazandınız.");
        renderMatrix(mapWithMine);
        return true;
    }

    void startGame() {  //fonksiyonlarımı topladığım fonksiyonum daha sonrasında mainim içinde çalıştırdığım fonksiyon
        launch();
        createMatrix();
        createMine();
        run();

    }

    void controlMatrixSize() {   //kullanıcı haritayı oluştururken girdiği verileri kontrol ettirdiğim metodum
        do {
            System.out.print("Satır sayısını giriniz (en az 2): ");
            row = input.nextInt();

            System.out.print("Sütun Sayısını giriniz (en az 2): ");
            col = input.nextInt();
            if (col < 2 || row < 2) {
                System.out.println("Hatalı matris değerleri girdiniz.");
            }
        } while (col < 2 || row < 2);
    }

    void renderMatrix(String[][] arr) { //mapi ekrana yazdıran fonksiyonum
        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");

            }
            System.out.println();

        }
    }

    int calculateMineCount(int userSelectRow, int userSelectCol) {  //etraftaki mayın sayısını hesapladığım fonksiyonum
        int mineCount = 0;  //Kullanıcının seçtiği konumun etrafında mayın var ise minecount artar
        for (int i = userSelectRow - 1; i <= userSelectRow + 1; i++) {
            for (int j = userSelectCol - 1; j <= userSelectCol + 1; j++) {
                if (i >= 0 && i < mapWithMine.length && j >= 0 && j < mapWithMine[0].length) {
                    if (mapWithMine[i][j].equals("*")) {
                        mineCount++;
                    }
                }
            }
        }
        return mineCount;
    }

}
