package ru.maxmishaev;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


public class Main {
    public static void main(String[] args) throws Exception {
        // Create a DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Create a new Document
        Document document = builder.newDocument();

        // Create root element
        Element root = document.createElement("staff");
        document.appendChild(root);

        // Create empoloyee elements and add text content
        Element empl1 = document.createElement("employee");
        root.appendChild(empl1);
        Element id1 = document.createElement("id");
        id1.appendChild(document.createTextNode("1"));
        empl1.appendChild(id1);
        Element firstName1 = document.createElement("firstName");
        firstName1.appendChild(document.createTextNode("John"));
        empl1.appendChild(firstName1);
        Element lastName1 = document.createElement("lastName");
        lastName1.appendChild(document.createTextNode("Smith"));
        empl1.appendChild(lastName1);
        Element country1 = document.createElement("country");
        country1.appendChild(document.createTextNode("USA"));
        empl1.appendChild(country1);
        Element age1 = document.createElement("age");
        age1.appendChild(document.createTextNode("25"));
        empl1.appendChild(age1);
        
        Element empl2 = document.createElement("employee");
        root.appendChild(empl2);
        Element id2 = document.createElement("id");
        id2.appendChild(document.createTextNode("2"));
        empl2.appendChild(id2);
        Element firstName2 = document.createElement("firstName");
        firstName2.appendChild(document.createTextNode("Ivan"));
        empl2.appendChild(firstName2);
        Element lastName2 = document.createElement("lastName");
        lastName2.appendChild(document.createTextNode("Petrov"));
        empl2.appendChild(lastName2);
        Element country2 = document.createElement("country");
        country2.appendChild(document.createTextNode("RU"));
        empl2.appendChild(country2);
        Element age2 = document.createElement("age");
        age2.appendChild(document.createTextNode("23"));
        empl2.appendChild(age2);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        StreamResult result = new StreamResult("output.xml");
        transformer.transform(source, result);

        System.out.println("XML file created successfully!");

        ArrayList<Employee> employees = new ArrayList<>();
        File xmlFile = new File("output.xml");
        //DocumentBuilderFactory
        factory = DocumentBuilderFactory.newInstance();
        //DocumentBuilder
        builder = factory.newDocumentBuilder();

        // Parse the XML file
        //Document
        document = builder.parse(xmlFile);
        // Access elements by tag name
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("employee");
        System.out.println(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println(node.getNodeName());
            NodeList nodeListEmployee = node.getChildNodes();
            Employee employee = new Employee();
            for (int j = 0; j < nodeListEmployee.getLength(); j++) {
                Node nodeEmployee = nodeListEmployee.item(j);
                System.out.println("Печатаем: " + nodeEmployee.getNodeName());
                System.out.println("          " + nodeEmployee.getTextContent());
                switch (nodeEmployee.getNodeName()) {
                    case "id":
                        employee.setId(Long.parseLong(nodeEmployee.getTextContent()));
                        break;
                    case "firstName":
                        employee.setFirstName(nodeEmployee.getTextContent());
                        break;
                    case "lastName":
                        employee.setLastName(nodeEmployee.getTextContent());
                        break;
                    case "country":
                        employee.setCountry(nodeEmployee.getTextContent());
                        break;
                    case "age":
                        employee.setAge(Integer.parseInt(nodeEmployee.getTextContent()));
                        break;
                }

            }
            System.out.println(employee + System.lineSeparator());
            employees.add(employee);
        }

        JSONArray jarr = new JSONArray();
        for (Employee empl : employees) {
            JSONObject jo = new JSONObject();
            jo.put("id", String.valueOf(empl.getId()));
            jo.put("firstName", empl.getFirstName());
            jo.put("lastName", empl.getLastName());
            jo.put("age", String.valueOf(empl.getAge()));
            jarr.add(jo);
        }
        try(PrintWriter pw = new PrintWriter(new java.io.FileOutputStream("data.json"))) {
            pw.write(jarr.toJSONString());
            pw.flush();
        } catch(java.io.FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}