package be.iccbxl.pid.Controller;

import be.iccbxl.pid.Dto.ShowDTO;
import be.iccbxl.pid.Model.Artist;
import be.iccbxl.pid.Model.ArtistType;
import be.iccbxl.pid.Model.Show;
import be.iccbxl.pid.Service.ShowService;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ShowController {

    @Autowired
    ShowService service;

    @GetMapping("/shows")
    public String index(Model model) {
        List<Show> shows = service.getAll();
        model.addAttribute("shows", shows);
        model.addAttribute("title", "Liste des spectacles");
        return "show/index";
    }

    @GetMapping("/shows/{id}")
    public String show(Model model, @PathVariable("id") String id) {
        Show show = service.get(id);
        Map<String, ArrayList<Artist>> collaborateurs = new TreeMap<>();

        for (ArtistType at : show.getArtistTypes()) {
            String type = at.getType().getType();
            collaborateurs.computeIfAbsent(type, k -> new ArrayList<>()).add(at.getArtist());
        }

        model.addAttribute("collaborateurs", collaborateurs);
        model.addAttribute("show", show);
        model.addAttribute("title", "Fiche d'un spectacle");
        return "show/show";
    }

    // Nouvelle méthode pour générer le flux RSS des spectacles
    @GetMapping("/shows-rss")
    public ResponseEntity<String> getShowsRss() {
        try {
            List<ShowDTO> shows = service.getAllShowDTOs();
            SyndFeed feed = new SyndFeedImpl();
            feed.setFeedType("rss_2.0");
            feed.setTitle("Liste des spectacles");
            feed.setLink("http://localhost:8080/shows-rss");
            feed.setDescription("Flux des spectacles à venir");
            feed.setPublishedDate(new Date());

            List<SyndEntry> entries = new ArrayList<>();
            for (ShowDTO show : shows) {
                SyndEntry entry = new SyndEntryImpl();
                entry.setTitle(show.getTitle());
                entry.setLink("http://localhost:8080/shows/" + show.getTitle().replaceAll(" ", "-").toLowerCase());

                // Conversion de LocalDateTime à Date pour le flux RSS
                Date pubDate = Date.from(show.getDate().atZone(ZoneId.systemDefault()).toInstant());
                entry.setPublishedDate(pubDate);

                SyndContent description = new SyndContentImpl();
                description.setType("text/plain");
                description.setValue(show.getDescription());
                entry.setDescription(description);
                entries.add(entry);
            }

            feed.setEntries(entries);

            SyndFeedOutput output = new SyndFeedOutput();
            String rssFeed = output.outputString(feed);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/rss+xml; charset=UTF-8");

            return ResponseEntity.ok().headers(headers).body(rssFeed);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error generating RSS feed");
        }
    }
}
