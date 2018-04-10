//
//  ViewController.swift
//  FoodJunky
//
//  Created by g on 3/12/18.
//  Copyright © 2018 Gurjot. All rights reserved.
//

import UIKit
import SQLite3

class ViewController: UIViewController, UITableViewDataSource, UITableViewDelegate
{
    //table for the allergies list
    var db: OpaquePointer?
    @IBOutlet  var tableView: UITableView!
   
    @IBOutlet weak var Save: UIButton!
    
    func numberOfSections(in tableView: UITableView) -> Int
    {
        return 1
    }
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        tableView.dataSource = self
        
    
        
    }

    let myarray = ["Peanuts", "Milk", "Eggs", "Meat", "Rice","Sesame", "Soy", "Wheat", "Gluten" ]

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return myarray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell
    {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cellReuseIdentifier")!
        let text = myarray[indexPath.row]
        cell.textLabel?.text = text
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath)
    {
        if tableView.cellForRow(at: indexPath)?.accessoryType == UITableViewCellAccessoryType.checkmark
        {
            tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCellAccessoryType.none
        }
        else
        {
            tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCellAccessoryType.checkmark
        }
        
        
    }
    @IBAction func save(_ sender: UIButton)
    {
    for allergies in myarray{
        print("Hello, \(allergies)!")}
        
    }
    
    
    
    
    
    
    
    
}



