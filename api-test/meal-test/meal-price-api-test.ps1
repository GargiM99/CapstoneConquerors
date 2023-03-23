# author: Hamza
# date: 2023/03/13
# description: Tests the meal price api

$authBody = Get-Content -Raw -Path '.\authentication-input.json' 
$headers = @{ "Content-Type" = "application/json" }

$authUri = "http://localhost:8080/api/auth/authenticate"
$mealUri = "http://localhost:8080/api/meals"

$mealArr = Get-Content -Raw -Path '.\meal-price-input.json' | ConvertFrom-Json
$numPass = 0
$numFail = 0

#Authentication for JWT token 
try { $authResponse = Invoke-RestMethod -Uri $authUri -Method POST -Headers $headers -Body $authBody }
catch {Write-Output($_.Exception.Message)}

$jwtoken = $authResponse.token
$headers = @{
    "Content-Type"  = "application/json"
    "Authorization" = "Bearer $jwtoken"
}

#Test the meal prices
For ($i = 0; $i -lt $mealArr.Length; $i++) {
    
    $mealBody = $mealArr[$i] | ConvertTo-Json

    $date = Get-Date

    try {
        $response = Invoke-RestMethod -Uri $mealUri -Method POST  -Headers $headers -Body $mealBody
        $auth = $headers.Authorization

        if ($response.priceUpdated){
            "$date - $response - $auth" | Out-File .\meal-success.csv -Append 
        }
            

        else{
            "Meal API call: Could not update `nDate: $date`nInput: $mealBody" | Out-File .\meal-fail.csv -Append
        }
    }
    catch {
        $errorMessage = $_.Exception.Message
        $auth = $headers.Authorization
        "Test Auth API call: $errorMessage`nDate: $date`nInput: $auth`n" | Out-File .\meal-error.csv -Append
    }
}

$numTotal = $numPass + $numFail
Write-Output("Test finshed: $numPass/$numTotal logged in and $numFail/$numTotal were unable to login")