package be.iccbxl.pid.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import be.iccbxl.pid.Model.Artist;
import be.iccbxl.pid.Model.ArtistType;
import be.iccbxl.pid.Model.Show;
import be.iccbxl.pid.Service.ArtistService;
import be.iccbxl.pid.Service.ShowService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

@RestController
public class CSVController {

	@Autowired
	private ShowService showService;
	
	@Autowired
	private ArtistService artistService;
	

	@GetMapping("/exportCSV")
	public void exportCSV(HttpServletResponse response) {
	    try {
	        response.setContentType("text/csv");
	        response.setHeader("Content-Disposition", "attachment; filename=shows.csv");

	        List<Show> listShows = showService.getAll();

	        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

	        String[] csvHeader = { "id", "title", "description", "price", "artists" };
	        CellProcessor[] processors = new CellProcessor[] {
	            new Optional(), // Processor pour id
	            new Optional(), // Processor pour title
	            new Optional(), // Processor pour description
	            new Optional(), // Processor pour price
	            new Optional()  // Processor pour artists
	        };

	        csvWriter.writeHeader(csvHeader);

	        for (Show show : listShows) {
	            List<String> artists = new ArrayList<>();

	            for (ArtistType artistType : show.getArtistTypes()) {
	                Artist artist = artistType.getArtist();
	                if (artist != null) {
	                    String fullName = artist.getFirstname() + " " + artist.getLastname();
	                    artists.add(fullName);
	                }
	            }

	            String artistNamesStr = String.join(", ", artists);

	            CSVShow csvShow = new CSVShow(
	                show.getId(),
	                show.getTitle(),
	                show.getDescription(),
	                show.getPrice(),
	                artistNamesStr
	            );

	            csvWriter.write(csvShow, csvHeader, processors);
	        }
	        csvWriter.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@Data
	public class CSVShow {
	    private Long id;
	    private String title;
	    private String description;
	    private Double price;
	    private String artists;

	    public CSVShow(Long id, String title, String description, Double price, String artists) {
	        this.id = id;
	        this.title = title;
	        this.description = description;
	        this.price = price;
	        this.artists = artists;
	    }
	}







	

	

}
