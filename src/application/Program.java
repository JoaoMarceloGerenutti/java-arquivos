package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Product> lista = new ArrayList<>();

		System.out.println("Digite o caminho do arquivo: ");
		String origemArquivoStr = sc.nextLine();

		File origemArquivo = new File(origemArquivoStr);
		String origemPastaStr = origemArquivo.getParent();
		
		boolean sucesso = new File(origemPastaStr + "\\out").mkdir();
		
		String destinhoArquivoStr = origemPastaStr + "\\out\\summary.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(origemArquivoStr))) {

			String itemCsv = br.readLine();
			while (itemCsv != null) {

				String[] fields = itemCsv.split(",");
				String nome = fields[0];
				double preco = Double.parseDouble(fields[1]);
				int quantidade = Integer.parseInt(fields[2]);

				lista.add(new Product(nome, preco, quantidade));

				itemCsv = br.readLine();
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(destinhoArquivoStr))) {

				for (Product item : lista) {
					bw.write(item.getNome() + "," + String.format("%.2f", item.total()));
					bw.newLine();
				}

				System.out.println(destinhoArquivoStr + " CRIADO!");
				
			} catch (IOException e) {
				System.out.println("ERRO AO ESCREVER O ARQUIVO: " + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("ERRO AO LER O ARQUIVO: " + e.getMessage());
		}
		sc.close();
	}
}