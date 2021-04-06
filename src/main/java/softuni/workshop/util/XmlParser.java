package softuni.workshop.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <O> O parseXML (Class<O> objectClass, String filePath) throws JAXBException;

    <O> void exportXML (O object, Class<O> objectClass, String filePath) throws JAXBException;
}
