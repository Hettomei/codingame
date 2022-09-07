VERSION 5.00
Begin VB.Form Form1 
   BackColor       =   &H00FFFFFF&
   BorderStyle     =   0  'None
   Caption         =   "Form1"
   ClientHeight    =   11520
   ClientLeft      =   0
   ClientTop       =   0
   ClientWidth     =   15360
   LinkTopic       =   "Form1"
   ScaleHeight     =   11520
   ScaleWidth      =   15360
   ShowInTaskbar   =   0   'False
   StartUpPosition =   3  'Windows Default
   WindowState     =   2  'Maximized
   Begin VB.Timer Temp 
      Left            =   480
      Top             =   2160
   End
   Begin VB.Timer Timer1 
      Interval        =   10
      Left            =   3840
      Top             =   3600
   End
   Begin VB.Label Résultats 
      Height          =   1095
      Left            =   5040
      TabIndex        =   3
      Top             =   2880
      Width           =   1455
   End
   Begin VB.Label memory 
      Height          =   495
      Left            =   2640
      TabIndex        =   2
      Top             =   1920
      Width           =   1815
   End
   Begin VB.Label Label2 
      Caption         =   "Label2"
      Height          =   735
      Left            =   3840
      TabIndex        =   1
      Top             =   1080
      Width           =   2655
   End
   Begin VB.Line Line3 
      Index           =   9
      X1              =   0
      X2              =   480
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line3 
      Index           =   8
      X1              =   0
      X2              =   480
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line3 
      Index           =   7
      X1              =   0
      X2              =   480
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line3 
      Index           =   6
      X1              =   0
      X2              =   480
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line3 
      Index           =   5
      X1              =   0
      X2              =   480
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line3 
      Index           =   4
      X1              =   0
      X2              =   480
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line3 
      Index           =   3
      X1              =   0
      X2              =   480
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line3 
      Index           =   2
      X1              =   0
      X2              =   480
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line3 
      Index           =   1
      X1              =   0
      X2              =   480
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line3 
      Index           =   0
      X1              =   3840
      X2              =   4320
      Y1              =   2880
      Y2              =   2880
   End
   Begin VB.Line Line2 
      Index           =   9
      X1              =   0
      X2              =   960
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line2 
      Index           =   8
      X1              =   0
      X2              =   960
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line2 
      Index           =   7
      X1              =   0
      X2              =   960
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line2 
      Index           =   6
      X1              =   0
      X2              =   960
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line2 
      Index           =   5
      X1              =   0
      X2              =   960
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line2 
      Index           =   4
      X1              =   0
      X2              =   960
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line2 
      Index           =   3
      X1              =   0
      X2              =   960
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line2 
      Index           =   2
      X1              =   0
      X2              =   960
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line2 
      Index           =   1
      X1              =   0
      X2              =   960
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line2 
      Index           =   0
      X1              =   1800
      X2              =   2760
      Y1              =   3840
      Y2              =   3840
   End
   Begin VB.Line Line1 
      Index           =   9
      X1              =   0
      X2              =   720
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line1 
      Index           =   8
      X1              =   0
      X2              =   720
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line1 
      Index           =   7
      X1              =   0
      X2              =   720
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line1 
      Index           =   6
      X1              =   0
      X2              =   720
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line1 
      Index           =   5
      X1              =   0
      X2              =   720
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line1 
      Index           =   4
      X1              =   0
      X2              =   720
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line1 
      Index           =   3
      X1              =   0
      X2              =   720
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line1 
      Index           =   2
      X1              =   0
      X2              =   720
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line1 
      Index           =   1
      X1              =   0
      X2              =   720
      Y1              =   0
      Y2              =   0
   End
   Begin VB.Line Line1 
      BorderColor     =   &H80000007&
      Index           =   0
      X1              =   600
      X2              =   1320
      Y1              =   3360
      Y2              =   3360
   End
   Begin VB.Label Label1 
      Alignment       =   2  'Center
      Height          =   495
      Left            =   600
      TabIndex        =   0
      Top             =   240
      Width           =   1335
   End
   Begin VB.Shape Shape1 
      Height          =   1215
      Left            =   1320
      Shape           =   3  'Circle
      Top             =   1200
      Width           =   1455
   End
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
'Integer
Public xxp As Double
Public xx As Integer
Public a As Integer
Public arron As Integer
Public b As Integer
Public d As Integer
Public memor As Integer

'Double
Public rand As Double
Public Temps As Double

'Boolean
Public cliq As Boolean
Public visibl As Boolean
Public desen As Boolean
Public bas As Boolean
Public modman As Boolean

Private Sub Form_DblClick()

'Envoi des salves en continu

If Line3(0).Y1 < 0 Then
desen = True
End If

End Sub

Private Sub Form_Load()

'Initialisation des variables

cliq = False
visibl = False
desen = False
sens = False
modman = False

'Initialisation de la fenetre des résultats

Résultats.Height = 1000
Résultats.Width = 4000

'Le sonar

Shape1.Top = -480
Shape1.Left = 0
Shape1.Height = 960
Shape1.Width = 960

'Informations d'utilisation

Label1.Caption = "Informations" & Chr(13) & "d'utilisation"
Label1.Left = 10
Label1.Top = 10800

'Génération du fond de manière aléatoire

        'Le fond horizontal

Randomize
For a = 0 To 9
rand = Rnd()
Line1(a).Y1 = 9000 + rand * 1000
Line1(a).Y2 = Line1(a).Y1
Line1(a).X1 = a * 1550
Line1(a).X2 = (a + 1) * 1550
Next a

        'Les tranches
        
For a = 0 To 9
Line2(a).X1 = (a + 1) * 1550
Line2(a).X2 = Line2(a).X1
Line2(a).Y1 = Line1(a).Y2
If a < 9 Then
Line2(a).Y2 = Line1(a + 1).Y1
End If
Next a

        'Les salves
        
For a = 0 To 9
Line3(a).X1 = 0
Line3(a).X2 = 0
Line3(a).Y1 = -a * 100
Line3(a).Y2 = -a * 100
Next a

End Sub

Private Sub Form_KeyPress(KeyAscii As Integer)

If KeyAscii = 27 Then End

'Affichage de la page des résultats

'Modification du fond manuellement

If modman = False Then
If KeyAscii = 109 Then
modman = True
End If
Else
If KeyAscii = 109 Then
modman = False
End If
End If

'Modification du fond automatiquement

If KeyAscii = 97 Or KeyAscii = 65 Then
If visibl = False Then

'Génération du fond de manière aléatoire

        'Le fond horizontal

Randomize
For a = 0 To 9
rand = Rnd()
Line1(a).Y1 = 9000 + rand * 1000
Line1(a).Y2 = Line1(a).Y1
Line1(a).X1 = a * 1550
Line1(a).X2 = (a + 1) * 1550
Next a

        'Les tranches
        
For a = 0 To 9
Line2(a).X1 = (a + 1) * 1550
Line2(a).X2 = Line2(a).X1
Line2(a).Y1 = Line1(a).Y2
If a < 9 Then
Line2(a).Y2 = Line1(a + 1).Y1
End If
Next a

End If

End If

End Sub

Private Sub Form_MouseDown(Button As Integer, Shift As Integer, X As Single, Y As Single)

If Button = 1 Then
If Temps = 0 Then
Else
Temps = 0
End If
memor = X
cliq = True
End If

If cliq = True Then
If modman = True Then Exit Sub
For a = 0 To 9
Line3(a).X1 = memor - 50
Line3(a).X2 = memor + 50
Next a
End If

End Sub

Private Sub Form_MouseMove(Button As Integer, Shift As Integer, X As Single, Y As Single)

'Les résultats vont suivre le sonar

If X > 11000 Then
Résultats.Left = X - 4480
Else
Résultats.Left = X + 480
End If
Résultats.Top = 0

'Mémorisation de la largueur

If visibl = True Then
Shape1.Left = memor
End If

Y = 0

'Le sonar suit la souris

Shape1.Left = X - 480

'Détermine la tranche de la position du sonar

xxp = memor / 1550
For arron = 0 To 9
If arron < xxp And xxp < arron + 1 Then
xx = arron
End If
Next arron

'Ne pas bouger la souris quand on envoi la salve

If visibl = True Then
Exit Sub
End If

End Sub

Private Sub Label1_Click()

'Affiche la page d'information d'utilisation

Form2.Visible = True
If Form2.Visible = True Then
Form2.Visible = False
Form2.Visible = True
End If

End Sub

Private Sub Label1_DblClick()

'Affiche la page d'information d'utilisation

Form2.Visible = True

If Form2.Visible = True Then

Form2.Visible = False
Form2.Visible = True
End If

End Sub

Private Sub timer1_timer()

'Affichage des résultats

Résultats.Caption = "Vitesse du son dans l'eau : 1250 m/s" & Chr(13) & "Temps " & Temps & " s"
If visibl = True Then
Temp.Interval = 100
Else
Temp.Interval = 0
End If

If Temp.Interval = 0 Then
Résultats.Caption = Résultats.Caption & Chr(13) & "Donc un aller : " & Temps / 2 & " s" & Chr(13) & "De plus distance sonar/fond : d(m) = v(m/s) x t(s)" & Chr(13) & "D'où distance = 1254 x " & Temps / 2 & " = " & (Temps / 2 * 1250) & " m"
End If

Label2.Caption = d

'Sens de mouvement des salves

If Line3(0).Y1 > Line3(1).Y1 Then
sens = True
End If

If Line3(0).Y1 <= 0 Then
visibl = False
Else
visibl = True
End If

'Envoie de la salve

If cliq = True Then
        If desen = False Then
        d = 50
        Else
        d = -50
        End If
Else
d = 0
End If

If Line3(0).Y1 < 0 Then
desen = False
End If

For a = 0 To 9
Line3(a).Y1 = Line3(a).Y1 + d
Line3(a).Y2 = Line3(a).Y2 + d
If Line3(a).Y1 < -a * 100 Then
cliq = False
End If
Next a

'La salve ne doit pas dépasser le fond

For a = 0 To 9
If Line3(a).Y1 > Line1(xx).Y1 Then
desen = True
End If
Next a

'Ne pas envoyer la salve si on change le fond manuellement

If modman = True Then
cliq = False
End If

End Sub

Public Sub temp_timer()

Temps = Temps + 0.1
If modman = True Then
Exit Sub
End If

End Sub


