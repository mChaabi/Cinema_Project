package Cinema.Movie.service;

import Cinema.Movie.model.Media;
import Cinema.Movie.repository.MediaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MediaService extends AbstractService<Media, Long> {

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    protected JpaRepository<Media, Long> getRepository() {
        return mediaRepository;
    }

    public String resolveVimeoThumbnail(String videoUrl) {
        try {
            Pattern p = Pattern.compile("vimeo\\.com/(\\d+)");
            Matcher m = p.matcher(videoUrl);
            if (!m.find()) return null;

            RestTemplate restTemplate = new RestTemplate();
            String oembedUrl = "https://vimeo.com/api/oembed.json?url=" + videoUrl;
            Map response = restTemplate.getForObject(oembedUrl, Map.class);
            return response != null ? (String) response.get("thumbnail_url") : null;
        } catch (Exception e) {
            return null;
        }
    }


}
