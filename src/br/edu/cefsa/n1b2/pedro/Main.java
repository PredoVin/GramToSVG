package br.edu.cefsa.n1b2.pedro;

import java.lang.reflect.Array;
import java.nio.file.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception
    {
        if(!Files.exists(Path.of("SVGOut.html")))
        {
            Files.createFile(Path.of("SVGOut.html"));
        }
        List<String> grammar = Files.readAllLines(Path.of("Grammar.txt"));
        int numberOfIterations = 0;
        String processes = "";
        double angle = 0;
        String[] variableAndSubstitute = new String[] {};


        int grammarCounter = 0;
        for(String line : grammar)
        {
            switch (grammarCounter)
            {
                case(1):
                {
                    if(line.toCharArray()[0] != 'n')
                    {
                        throw new Exception("Grammar is malformed");
                    }
                    else
                    {
                        numberOfIterations = Integer.valueOf((line.substring(line.indexOf(':') + 1)).trim());
                    }
                    break;
                }
                case(2):
                {
                    if(line.toCharArray()[0] != 'w')
                    {
                        throw new Exception("Grammar is malformed");
                    }
                    else
                    {
                        processes = line.substring(line.indexOf(':') + 1).trim();
                    }
                    break;
                }
                case(3):
                {
                    if(line.toCharArray()[0] != 'a')
                    {
                        throw new Exception("Grammar is malformed");
                    }
                    else
                    {
                        angle = Math.toRadians(Double.valueOf((line.substring(line.indexOf(':') + 1)).trim()));
                    }
                    break;
                }
                case(4):
                {
                    if(line.toCharArray()[0] != 'p')
                    {
                        throw new Exception("Grammar is malformed");
                    }
                    else
                    {
                        variableAndSubstitute = (line.substring(line.indexOf(':') + 1)).trim().split(">");
                    }
                    break;
                }
            }
            grammarCounter++;
        }

        double standardLineLength = (100/(numberOfIterations*1.5));
        double height = 500 + (300*numberOfIterations*numberOfIterations);
        double width = 500 + (300*numberOfIterations*numberOfIterations);
        double presentX = height/2;
        double presentY = width/2;
        double presentAngle = Math.toRadians(0);
        String svgOut = "<html><body><svg height=\""+ height + "\" width=\"" + width + "\" transform=\"rotate(270)\">\r\n";

        for(int i = 0; i < numberOfIterations; i++)
        {
            processes = processes.replace(variableAndSubstitute[0], variableAndSubstitute[1]);
        }

        for(char process : processes.toCharArray())
        {
            System.out.print("\n" + process);
            if(process == '+')
            {
                presentAngle += angle;
            }
            else if(process == '-')
            {
                presentAngle -= angle;
            }
            else
            {
                System.out.print(" => " + presentAngle);
                //lineExample = <line x1="0" y1="0" x2="200" y2="200" style="stroke:rgb(0,0,0);stroke-width:2" />
                svgOut += "<line x1=\"" + presentY + "\" y1=\"" + presentX + "\" x2=\"" + (presentY + (standardLineLength * Math.sin(presentAngle))) + "\" y2=\"" + (presentX + (standardLineLength * Math.cos(presentAngle))) + "\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />\r\n";
                presentX = (presentX + (standardLineLength * Math.cos(presentAngle)));
                presentY = (presentY + (standardLineLength * Math.sin(presentAngle)));
            }
        }
        svgOut += "</svg></body></html>";
        Files.writeString(Path.of("SVGOut.html"), svgOut);
    }
}
