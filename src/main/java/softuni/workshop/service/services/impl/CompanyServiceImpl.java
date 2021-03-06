package softuni.workshop.service.services.impl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.dtos.CompanyDto;
import softuni.workshop.service.services.dtos.CompanyRootDto;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final static String COMPANIES_PATH = "src/main/resources/files/xmls/companies.xml";
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importCompanies() throws JAXBException {
        CompanyRootDto companyRootDto = this.xmlParser.parseXML(CompanyRootDto.class, COMPANIES_PATH);

        for(CompanyDto dto : companyRootDto.getCompanyDtoList()){
            this.companyRepository.saveAndFlush(this.modelMapper.map(dto, Company.class));
        }
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() {
        try {
           return String.join("\n", Files.readAllLines(Path.of(COMPANIES_PATH)));
        }catch (IOException e){
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public Company findByName(String name) {
        return this.companyRepository.findByName(name);
    }
}
