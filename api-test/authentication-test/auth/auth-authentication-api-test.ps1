# author: Hamza
# date: 2023/03/09
# description: Tests the auth api on the ttms backend

$authArr = Get-Content -Raw -Path '.\authentication-input.json' | ConvertFrom-Json
$jwtokens = @()

$authUri = "http://localhost:8080/api/auth/authenticate"
$mealUri = "http://localhost:8080/api/meals"

$headers = @{ "Content-Type" = "application/json" }
$numPass = 0
$numFail = 0


#Tests the authentication 
For ($i = 0; $i -lt $authArr.Length; $i++) {
    $authBody = $authArr[$i] | ConvertTo-Json
    $date = Get-Date

    try {
        $jwtoken = Invoke-RestMethod -Uri $authUri -Method POST -Headers $headers -Body $authBody 
        $jwtokens += $jwtoken.token
        $numPass++
    }
    catch {
        $errorMessage = $_.Exception.Message
        "Authenticate API call: $errorMessage`nDate: $date`nInput: $authBody`n" | Out-File .\auth-error.csv -Append
        $numFail++
    }
}

#Test the tokens
For ($i = 0; $i -lt $jwtokens.Length; $i++) {
    
    $jwtoken = $jwtokens[$i]

    $date = Get-Date
    $headers = @{
        "Content-Type" = "application/json"
        "Authorization" = "Bearer $jwtoken"
    }

    try {
        $authOut = Invoke-RestMethod -Uri $mealUri -Method GET -Headers $headers
        $auth = $headers.Authorization
        "$date - $authOut - $auth" | Out-File .\auth-success.csv -Append 
    }
    catch {
        $errorMessage = $_.Exception.Message
        $auth = $headers.Authorization
        "Test Auth API call: $errorMessage`nDate: $date`nInput: $auth`n" | Out-File .\token-error.csv -Append
    }
}

$numTotal = $numPass + $numFail
Write-Output("Test finshed: $numPass/$numTotal logged in and $numFail/$numTotal were unable to login")