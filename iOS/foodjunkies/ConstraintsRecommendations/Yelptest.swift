//
//  Yelptest.swift
//  ConstraintsRecommendations
//
//  Created by g on 5/8/18.
//  Copyright © 2018 Ivelisse Morales. All rights reserved.
//


import Alamofire

import SwiftyJSON

public class YelpAPIClient: NSObject {

    
    private let apiKey: String!
    private lazy var manager: Alamofire.SessionManager = {
        if let apiKey = self.apiKey, apiKey != "" {
            var headers = Alamofire.SessionManager.defaultHTTPHeaders
            headers["Authorization"] = "Bearer \(apiKey)"
            let configuration = URLSessionConfiguration.default
            configuration.httpAdditionalHeaders = headers
            return Alamofire.SessionManager(configuration: configuration)
        } else {
            return Alamofire.SessionManager()
        }
    }()
    
    public func getBusinessInfo(id: String?, completion: @escaping (_ jsonResponse: JSON) -> ()) {
        assert((id != nil && id != ""), "Business ID must not be empty.")
        
        if self.isAuthenticated() {
            let param: Parameters = [:]
            
            self.manager.request(YelpAPIRouter.business(id: id!, parameters: param)).responseJSON { (response) in
                switch response.result {
                case .success(let data):
                    let json = JSON(data)
                    completion(json)
                case .failure(let error):
                    print("Request failed with error: \(error)")
                }
            }
        }
    }
    
    public func searchBusinesses(term: String?, latitude: Double?, longitude: Double?, radius: Int?, offset: Int?, sortBy: String?, completion: @escaping (_ jsonResponse: JSON) -> ()) {
        assert((latitude != nil && longitude != nil), "Input latitude or longitude must not be null.")
        assert(radius != nil, "Input radius can not be null.")
        
        if self.isAuthenticated() {
            let param: Parameters = [
                "term": term,
                "latitude": latitude,
                "longitude": longitude,
                "radius": radius,
                "offset": offset
            ]
            self.manager.request(YelpAPIRouter.search(parameters: param)).responseJSON { (response) in
                switch response.result {
                case .success(let data):
                    let json = JSON(data)
                    completion(json)
                case .failure(let error):
                    print("Request failed with error: \(error)")
                }
                //        self.manager.session.finishTasksAndInvalidate() // May need to retain manager instance?
            }
        }
    }
    
    public init(apiKey: String!) {
        assert((apiKey != nil && apiKey != ""), "An API key is required to query the Yelp Fusion API.")
        self.apiKey = apiKey
        super.init()
    }
    
    public func isAuthenticated() -> Bool {
        if let _ = self.apiKey, self.apiKey != "" {
            return true
        }
        return false
    }
}



