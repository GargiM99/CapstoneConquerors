# author: Hamza
# date: 2023/03/09
# description: Tests the auth api on the ttms backend

$registerArr = Get-Content -Raw -Path 'register-input.json' | ConvertFrom-Json

$uri = "http://localhost:8080/api/auth/authenticate"
$headers = @{ "Content-Type" = "application/json" }

#Creates 
For ($i = 0; $i -lt $registerArr.Length; $i++) {

    $registerBody = $registerArr[$i] | ConvertTo-Json

    try {
        Write-Output($registerBody)
        Invoke-RestMethod -Uri $uri -Method POST -Headers $headers -Body $registerBody | Write-Output
    }
    catch {
        Write-Output $_.Exception.Message
    }
}