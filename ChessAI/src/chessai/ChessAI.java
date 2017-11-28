/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessai;

import java.util.Scanner;

/**
 *
 * @author Ryan and Parm
 */
public class ChessAI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
	Passer p = new Passer();//Not really needed
	Board b = new Board(p);
        int[] ogPos = new int[2];
        int[] newPos = new int[2];
        System.out.print("Choose piece to move and position to move it to(eg. A:6-A:4): ");
        String[] pos = new String[4];
        pos = input.nextLine().split(":|-");
        ogPos[0] = (int)pos[0].charAt(0)-65;
        ogPos[1] = Integer.parseInt(pos[1]);
        newPos[0] = (int)pos[2].charAt(0)-65;
        newPos[1] = Integer.parseInt(pos[3]);
        b.movePiece(ogPos, newPos);
    }
    
}
