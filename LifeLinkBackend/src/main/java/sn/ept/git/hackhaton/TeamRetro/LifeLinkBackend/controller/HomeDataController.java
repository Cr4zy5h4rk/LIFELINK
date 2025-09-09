package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.BloodDonationCampaignService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.EmergencyService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.PostService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.StatisticsService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.StatisticsDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class HomeDataController {

    private final EmergencyService emergencyService;

    private final BloodDonationCampaignService campaignService;

    private final PostService postService;

    private final StatisticsService statisticsService;

    @Autowired
    HomeDataController(
            EmergencyService emergencyService,
            BloodDonationCampaignService campaignService,
            PostService postService,
            StatisticsService statisticsService
    ){
        this.emergencyService = emergencyService;
        this.campaignService = campaignService;
        this.postService = postService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/home")
    public ResponseEntity<Map<String, Object>> getHomeData() {
        Map<String, Object> response = new HashMap<>();

        response.put("emergencies", emergencyService.getLatestPendingEmergencies());
        response.put("campaigns", campaignService.getLatestActiveCampaigns());
        response.put("testimonies", postService.getRecentTestimonies());
        response.put("statistics", statisticsService.getCurrentStatistics());
        response.put("recentPosts", postService.getRecentPosts());

        return ResponseEntity.ok(response);
    }

}
