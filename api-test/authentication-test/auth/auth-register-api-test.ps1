#Run once if there is no users

$regUri = "http://localhost:8080/api/auth/register"
$registerArr = Get-Content -Raw -Path '.\register-input.json' | ConvertFrom-Json

$headers = @{ "Content-Type" = "application/json" }

#Register user for testing authentication
For ($i = 0; $i -lt $registerArr.Length; $i++) {
    $registerBody = $registerArr[$i] | ConvertTo-Json
    try {
        Invoke-RestMethod -Uri $regUri -Method POST -Headers $headers -Body $registerBody
    }
    catch {Write-Output $_.Exception.Message}
}
