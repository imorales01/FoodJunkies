//
//  Login.swift
//  ConstraintsRecommendations
//
//  Created by g on 5/2/18.
//  Copyright © 2018 Ivelisse Morales. All rights reserved.
//

import UIKit
class Login : UIViewController
{
    let URL_Register = "http://54.208.66.68:80/user_control2.php"
    // need to define the url to use as parameter
    @IBOutlet weak var textFieldName: UITextField!
    @IBOutlet weak var textFieldPassword: UITextField!
    // Text field objects for email and password
    // action when clicking register button
    @IBAction func buttonClick(_ sender: UIButton) {
        let requestURL = NSURL(string: URL_Register)
        let request = NSMutableURLRequest( url: requestURL! as URL)
        request.httpMethod = "POST"
        let email = textFieldName.text
        let password = textFieldPassword.text
        
        let postParameters = "Email="+email!+"&Password="+password!;
        request.httpBody = postParameters.data(using: String.Encoding.utf8)
        let task = URLSession.shared.dataTask(with: request as URLRequest)
        {
            data, response, error in
            if error != nil
            {
                print("error is \(error)" )
                return;
            }
            
            do
            {
                let myJSON = try JSONSerialization.jsonObject(with: data!, options: .mutableContainers) as? NSDictionary
                
                if let parseJSON = myJSON
                {
                    var msg : String!
                    msg = "good"
                    print(msg)
                }
            }
            catch
            { print(error)}
        }
    
        task.resume()
    }
    
}
