//
//  OutputYelpTester.swift
//  ConstraintsRecommendations
//
//  Created by g on 5/8/18.
//  Copyright © 2018 Ivelisse Morales. All rights reserved.
//

import UIKit

class OutputYelpTester: UIViewController
{
    var api:YelpAPIClient!
    var businesses: [Business] = []
    override func viewDidLoad() {
        super.viewDidLoad()
        api = YelpAPIClient(apiKey: "oNavXIvI6AgPNIM-fsgq2EBTWIJJKySS0yE-t-ANnOMTaJKiJJI1gT_DssXmcRCgVOgYQZQ8Jx2vPlnQ-jbjSrdaccAUT1-Qkap-wkBwQZ9MSVy_E39a1ekSI_rpWnYx")
        refreshBusinesses(api: api)
        

    }
    func refreshBusinesses(api: YelpAPIClient) {
        if api.isAuthenticated() {
            api.searchBusinesses(term: "pizza", latitude: 22.3204, longitude: 114.1698, radius: 40000, offset: 20, sortBy: "distance") { (json) in
                    self.businesses += Business.businesses(json: json)
                print(self.businesses[0].name)
                print(self.businesses[1].name)
                print(self.businesses[2].name)
                }
            }
        }
    
    
    
}


