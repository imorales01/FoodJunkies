//
//  ViewController.swift
//  ConstraintsRecommendations
//
//  Created by Ivelisse Morales on 4/8/18.
//  Copyright © 2018 Ivelisse Morales. All rights reserved.
//

import UIKit
import SQLite3

class ViewController: UIViewController {
    
    var db: OpaquePointer?

    @IBOutlet weak var budgetLabel: UILabel!
    @IBAction func budgetSlider(_ sender: UISlider) {
        //budgetLabel.text = "$ " + String(Int(sender.value))
        budgetLabel.text = String(Int(sender.value)) + " Dollars"
    }
    
    @IBOutlet weak var distanceLabel: UILabel!
    @IBAction func distanceSlider(_ sender: UISlider) {
        distanceLabel.text = String(Int(sender.value)) + " Miles"
    }
    
    @IBOutlet weak var timeLabel: UILabel!
    @IBAction func timeSlider(_ sender: UISlider) {
        timeLabel.text = String(Int(sender.value)) + " Mins"
    }
    
    @IBAction func doneButton(_ sender: Any) {
        // here we wil save the values
        let budget = budgetLabel.text
        //print(String(budget!))
        let distance = distanceLabel.text
        let time = timeLabel.text
        // insert values into SQLite db
        if (budget?.isEmpty)!{
            print("Budget Not Chosen")
            return;
        }
        if (distance?.isEmpty)!{
            print("Distance Not Chosen")
            return;
        }
        if (time?.isEmpty)!{
            print("Budget Not Chosen")
            return;
        }
        
        var stmt: OpaquePointer?
        
        let insertQuery = "INSERT INTO FoodJunkies (budget, distance, time) VALUES (?, ?, ?)"
        
        if sqlite3_prepare(db, insertQuery, -1, &stmt, nil) != SQLITE_OK {
            print("Error Binding Query")
        }
        
        // the number referece to the question mark position
        if sqlite3_bind_int(stmt, 1, (budget! as NSString).intValue) != SQLITE_OK {
            print ("Error Binding Budget!")
        }
        
        if sqlite3_bind_int(stmt, 2, (distance! as NSString).intValue) != SQLITE_OK {
            print ("Error Binding Distance!")
        }
        
        if sqlite3_bind_int(stmt, 3, (time! as NSString).intValue) != SQLITE_OK {
            print ("Error Binding Time!")
        }
        
        // execute the query to insert the values
//        if sqlite3_step(stmt) == SQLITE_DONE {
//            print ("FoodJunkies saved Successfully")
//            query()
//        }
        
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view, typically from a nib.
        let fileURL = try!
            FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false).appendingPathComponent("FoodJunkies.sqlite")
        if sqlite3_open(fileURL.path, &db) != SQLITE_OK{
            print("Error Opening Database!")
            return
        }
        
        let createTableQuery = "CREATE TABLE IF NOT EXISTS FoodJunkies (id INTEGER PRIMARY KEY AUTOINCREMENT, budget INTEGER, distance INTEGER, time INTEGER)"
        
        //EXECUTE IN DB
        if sqlite3_exec(db, createTableQuery, nil, nil, nil) != SQLITE_OK  {
            print("Error Creating Table!")
            return
        }
        //insert values inside table
        print("Everything is fine")
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func query() {
        let queryStatementString = "SELECT * FROM FoodJunkies"
        var queryStatement: OpaquePointer? = nil
        if sqlite3_prepare_v2(db, queryStatementString, -1, &queryStatement, nil) == SQLITE_OK {
            
            while (sqlite3_step(queryStatement) == SQLITE_ROW) {
                let id = sqlite3_column_int(queryStatement, 0)
                let budget = sqlite3_column_int(queryStatement, 1)
                let distance = sqlite3_column_int(queryStatement, 2)
                let time = sqlite3_column_int(queryStatement, 3)
                print("Query Result:")
                print("\(id) | \(budget) | \(distance) | \(time)")
            }
            
        } else {
            print("SELECT statement could not be prepared")
        }
        sqlite3_finalize(queryStatement)
    }

    func delete() {
        let deleteStatementStirng = "DELETE FROM FoodJunkies WHERE Id = 1;"
        var deleteStatement: OpaquePointer? = nil
        if sqlite3_prepare_v2(db, deleteStatementStirng, -1, &deleteStatement, nil) == SQLITE_OK {
            if sqlite3_step(deleteStatement) == SQLITE_DONE {
                print("Successfully deleted row.")
            } else {
                print("Could not delete row.")
            }
        } else {
            print("DELETE statement could not be prepared")
        }
        
        sqlite3_finalize(deleteStatement)
    }

}

