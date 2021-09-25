/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.agendaseparadores;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlitos
 */
public class Main {
    
    private void escribirArchivo(String nombre, String edad, String numero){
        try{
            FileWriter escritordeArchivo = new FileWriter("agenda.txt");

            String separador = "####";
            
            escritordeArchivo.write(nombre);
            escritordeArchivo.write(separador);
            escritordeArchivo.write(edad);
            escritordeArchivo.write(separador);
            escritordeArchivo.write(numero);
            escritordeArchivo.write(separador);
 
            escritordeArchivo.close();
        }catch(IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void leerArchivo(){
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader("agenda.txt"));
            String texto = br.readLine();
            while(texto != null){
                System.out.println(texto);
                texto = br.readLine();
            }
           
            br.close();
        } catch (FileNotFoundException ex){
            System.out.println("No encnontré el archivo solicitado");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            System.out.println("Tengo problemas para cerrar el archivo");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    private void escrituraBinaria(String nombre, String edad, String numero){               
        try{
            FileOutputStream archivo = new FileOutputStream("persona.bin");
            
            int i = nombre.length();
            
            if(i == 30){
                DataOutputStream data = new DataOutputStream(archivo);
                data.writeBytes(nombre);
                data.writeBytes(edad);
                data.writeBytes(numero);
            }else if(i < 30){
                String nuevo = "";
                for (int j = i ; j < 30; j++) {
                    nuevo = nuevo + " ";
                }
                DataOutputStream data = new DataOutputStream(archivo);
                data.writeBytes(nombre + nuevo);
                data.writeBytes(edad);
                data.writeBytes(numero);
            }else if(i > 30){
                System.out.println("El nombre no puede tener más de 30 caracteres");
                DataOutputStream data = new DataOutputStream(archivo);
            }
            
            archivo.close();
        } catch(FileNotFoundException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void lecturaBinaria(){        
        try{
            FileInputStream archivo = new FileInputStream("persona.bin");
            
            DataInputStream data = new DataInputStream(archivo);
                        
            byte nombreBytes[] = data.readNBytes(30);
            byte edadBytes[] = data.readNBytes(2);
            byte numeroBytes[] = data.readNBytes(8);
            String nombre = new String(nombreBytes);
            System.out.print(nombre);
            
            String edad = new String(edadBytes);
            System.out.print(edad);
                        
            String telefono = new String(numeroBytes);
            System.out.print(telefono);
                       
            archivo.close();
        } catch(FileNotFoundException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main main = new Main();
        
        String nombre, edad, numero;     
        Scanner leer = new Scanner(System.in);
        
        System.out.println("-----------------------------------\n");
        System.out.println("Ingrese el nombre de la persona: ");
        nombre = leer.nextLine();                    
        System.out.println("Ingrese la edad de la persona: ");
        edad = leer.nextLine();
        System.out.println("Ingrese el número de la persona de ocho dígitos: ");
        numero = leer.nextLine();
        System.out.println("-----------------------------------");
        main.escrituraBinaria(nombre, edad, numero);
        main.escribirArchivo(nombre, edad, numero);
        
        System.out.println("\nArchivo secuencial con separador binarios: ");        
        main.leerArchivo();
        
        System.out.println("\nArchivo secuencial con separador por tamaño: ");        
        main.lecturaBinaria();
    }    
}
