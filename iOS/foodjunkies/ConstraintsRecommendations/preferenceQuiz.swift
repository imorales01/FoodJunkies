//
//  preferenceQuiz.swift
//  ConstraintsRecommendations
//
//  Created by Ivelisse Morales on 4/8/18.
//  Copyright © 2018 Ivelisse Morales. All rights reserved.
//

import UIKit

class preferenceQuiz: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {
    
    // this contains all of the data we want to display
    let array:[String] = ["american", "chinese", "european", "indian", "italian", "japanese", "korean", "mediterranean", "middleeastern", "spanish"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //Do any additional setup after loading the view, typically from a nib.
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // number of views: defines the number of views; how many items we want.
    public func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return array.count
    }
    
    //populate views
    public func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! myCell
        cell.myImageView.image = UIImage(named: array[indexPath.row] + ".JPG")
        cell.lblCell.text = array[indexPath.row].capitalized
        return cell
    }
}
