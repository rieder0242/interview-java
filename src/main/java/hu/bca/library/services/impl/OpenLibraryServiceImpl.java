package hu.bca.library.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bca.library.services.OpenLibraryService;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author riean
 */
@Service
public class OpenLibraryServiceImpl implements OpenLibraryService {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Integer getBookPublisYear(String workId) {
        try {
            final URL url = new URI(String.format("https://openlibrary.org/works/%s.json", URLEncoder.encode(workId, StandardCharsets.UTF_8))).toURL();
            JsonNode readTree = objectMapper.readTree(url);
            JsonNode firstPublishDateField = readTree.get("first_publish_date");
            if (firstPublishDateField == null) {
                return null;
            }
            final String firstPublishDate = firstPublishDateField.asText();
            Logger.getLogger(OpenLibraryServiceImpl.class.getName()).log(Level.INFO, firstPublishDate);
            if (firstPublishDate.length() >= 4) {
                String firstPublishYear = firstPublishDate.substring(firstPublishDate.length() - 4);
                return Integer.valueOf(firstPublishYear);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("The format of the first publish year of the %s book is wrong.", workId));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(OpenLibraryServiceImpl.class.getName()).log(Level.SEVERE, "Error in request", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Unable to retrieve data from openlibrary.org for book %s.", workId, ex));
        }
    }

}
