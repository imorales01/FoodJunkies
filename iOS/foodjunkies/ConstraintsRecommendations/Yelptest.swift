//
//  Yelptest.swift
//  ConstraintsRecommendations
//
//  Created by g on 5/8/18.
//  Copyright © 2018 Ivelisse Morales. All rights reserved.
//


import CDYelpFusionKit
import UIKit

final class CDYelpFusionKitManager: NSObject {
    
    static let shared = CDYelpFusionKitManager()
    
    var apiClient: CDYelpAPIClient!
    
    func configure() {
        // How to authorize using your clientId and clientSecret
        self.apiClient = CDYelpAPIClient(apiKey: "oNavXIvI6AgPNIM-fsgq2EBTWIJJKySS0yE-t-ANnOMTaJKiJJI1gT_DssXmcRCgVOgYQZQ8Jx2vPlnQ-jbjSrdaccAUT1-Qkap-wkBwQZ9MSVy_E39a1ekSI_rpWnYx")
    }
}
