//
//  recommend.swift
//  ConstraintsRecommendations
//
//  Created by Ivelisse Morales on 4/9/18.
//  Copyright © 2018 Ivelisse Morales. All rights reserved.
//

import UIKit

class recommend: UIViewController {

    let ratingURL = URL(string:"http://54.208.66.68:80/getRatings.php")
    let dishURL = URL(string:"http://54.208.66.68:80/getDishes.php")
    let dishNameURL = URL(string:"http://54.208.66.68:80/getDishName.php")
    
    let dishes:[String] = ["pizza", "lasana", "spagetti", "parmasian", "calzon", "paella", "empanadas", "chorizo", "gazpacho", "pisto", "dim sum", "dumplings", "congee", "seasame chicken", "lo mein", "samosa", "butter chicken", "naan", "tikka masala", "biryani", "ramen" , "sushi", "takoyaki" , "karaage", "miso soup", "kebab", "falafel", "baked halibet", "cilantro lime chicken", "kofta kebab", "kuffta", "shish kebab", "kabsa", "kibbeh", "pizza","hamburger", "french fries", "steak", "meat loaf", "bulgogi", "kimbap", "japchae", "oax bone soup", "galbi", "sausage" ,"meatball", "potatoe dumplings", "goulash", "full breakfast"]
    
    @IBOutlet weak var dishRecomend: UILabel!
    @IBAction func recomendButton(_ sender: Any) {
        let randomNum = Int(arc4random_uniform(UInt32(dishes.count)))
        dishRecomend.text = dishes[randomNum].capitalized
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func getCuisine(){
        //var cuisineRating = getCuisineRating()
        //var cuisineID = recommendationAlgorithm(ratingArray: cuisineRating)
    }
    
    func recommendationAlgorithm(ratingArray : Array<Int>) -> Int{
        var sum = 0
        for i in ratingArray{
            sum = sum + i
        }
        let randomNum = Int(arc4random_uniform(UInt32(sum)))
        
        var prev_value = 0;
        var current_max_value = 0;
        var found_index = -1;
        for i in 1...ratingArray.count { //walk through the array
            current_max_value = prev_value + ratingArray[i];
    
            var found = true
            if randomNum >= prev_value && randomNum < current_max_value {
                found = true
            }
            else{
                found = false;
            }
            if found {
                found_index = i+1;
                break;
            }
            prev_value = current_max_value;
        }
        
        return found_index;
    }
    func getCuisineRating(){
        var request = URLRequest(url: ratingURL!)
        request.httpMethod = "GET"
        
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
