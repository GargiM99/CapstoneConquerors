# author: Hamza
# date: 2023/03/09
# description: Tests the register api on the ttms backend

$inputString = Get-Content -Raw -Path 'register-input.json'
$uri = "http://localhost:8080/api/auth/register"
$headers = @{ "Content-Type" = "application/json" }

$bodyArray = ConvertFrom-Json $inputString
$date = Get-Date
$successBody = "$date`n"
    
For ($i = 0; $i -lt $bodyArray.Length; $i++) {
    $jsonBody = $bodyArray[$i] | ConvertTo-Json
    $date = Get-Date
    
    try {
        $response = Invoke-RestMethod -Uri $uri -Method POST -Headers $headers -Body $jsonBody
        $successBody += "Input:`n$jsonBody`nResponse:`n$response`n"
        "$response" | Out-File .\register-tokens.csv -Append
    }
    catch {
        $errorMessage = $_.Exception.Message
        "Register API call: $errorMessage`nDate: $date`nInput: $jsonBody`n" | Out-File .\register-error.csv -Append
    }
}

$successBody | Out-File .\register-success.csv -Append