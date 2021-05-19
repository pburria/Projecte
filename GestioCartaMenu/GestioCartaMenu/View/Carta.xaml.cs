using MySQL;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Storage;
using Windows.Storage.Pickers;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=234238

namespace GestioCartaMenu.View
{
    /// <summary>
    /// Una página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class Carta : Page
    {
        public Carta()
        {
            this.InitializeComponent();
        }
        private List<Catagoria> categorias = Catagoria.GetCatagorias();
        private List<Plat> plats = Plat.GetPlat();
        private string[] comboDisponible = {"Disponible","No Disponible"};
        private BitmapImage image=null;

        private void lsvCategories_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lsvCategories.SelectedItem != null)
            {
                Catagoria c = (Catagoria)lsvCategories.SelectedItem;
                lsvPlats.ItemsSource = Plat.GetPlatPerCategoria(c.Codi);
            }
        }

        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            lsvCategories.ItemsSource = categorias;
            lsvPlats.ItemsSource = plats;
            cmbDisponible.ItemsSource = comboDisponible;
            cmbCategories.ItemsSource = Catagoria.GetCatagorias();
            
        }
        private void btnTotsPlats_Click(object sender, RoutedEventArgs e)
        {
            lsvPlats.ItemsSource = plats;
            lsvCategories.SelectedItem = null;
        }

        private void btnDeletePlat_Click(object sender, RoutedEventArgs e)
        {
            if (lsvPlats.SelectedValue != null)
            {
                Plat p = (Plat)lsvPlats.SelectedValue;
                mostrarDialogeBorrarPlat("Segur que vols borrar el plat: "+p.Nom,p);
            }
            else
            {
                mostrarDialoge("Seleccionar un plat per poder borrar-lo");
            }
        }
        private async void mostrarDialogeBorrarPlat(string txt,Plat p)
        {
            ContentDialog cd = new ContentDialog()
            {
                Content = txt,
                PrimaryButtonText = "OK",
                CloseButtonText = "CLOSE"
            };
            ContentDialogResult cdr = await cd.ShowAsync();
            if (cdr.Equals(ContentDialogResult.Primary)){
                long t = Plat.platAmbComanda(p.Codi);
                if (t > 0)
                {
                    mostrarDialoge("No es pot borrar el plat ja que esta referenciat a " + t + " comandes");
                }
                else
                {
                    Plat.DeleteLiniaEscandall(p.Codi);
                    bool a = Plat.DeletePlat(p.Codi);
                    if (a)
                    {
                        plats = Plat.GetPlat();
                        lsvPlats.ItemsSource = plats;
                        mostrarDialoge("Plat borrat correctament");
                    }
                    else
                    {
                        mostrarDialoge("No s'ha pogut borrar el plat");
                    }
                }     
            }
        }

        private async void mostrarDialoge(string txt)
        {
            ContentDialog cd = new ContentDialog()
            {
                Content = txt,
                PrimaryButtonText = "OK",
                CloseButtonText = "CLOSE"
            };
            ContentDialogResult cdr = await cd.ShowAsync();
        }

        private void btnFiltre_Click(object sender, RoutedEventArgs e)
        {
            if (txbTextFiltre.Text.Length == 0)
            {
                mostrarDialoge("Introduir un text per poder filtrar per nom");
            }
            else
            {
                string a = txbTextFiltre.Text;
                lsvPlats.ItemsSource=Plat.GetPlatPerNom(a.ToUpper());
                txbTextFiltre.Text = "";
            }
        }

        private void btnFoto_Click(object sender, RoutedEventArgs e)
        {
            btnFile1_Click();

        }

        private void btnInserirPlat_Click(object sender, RoutedEventArgs e)
        {
            int codi = Plat.GetUltimCodi();
            decimal preu = Decimal.Parse(txbPreu.Text);
            string ruta = image.UriSource.AbsolutePath;

            string foto = ruta;
            bool a=false;
            Catagoria c = (Catagoria)cmbCategories.SelectedValue;
            string dis = (string)cmbDisponible.SelectedValue;
            if (dis == "Disponible")
            {
                a = true;
            }
            else
            {
                a = false;
            }
            bool b=Plat.InsertPlat(codi+1, txbNom.Text, txbDescripcio.Text, preu, foto, a, c.Codi);
            if (b)
            {
                plats = Plat.GetPlat();
                lsvPlats.ItemsSource = plats;
                natejar();
                mostrarDialoge("Plat inserit correctament");
            }
            else
            {
                mostrarDialoge("Plat no inserit");
            }
        }

        private void txbNom_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (txbNom.Text.Length > 50 || txbNom.Text == "ERROR")
            {
                errNom.Text = "Max 50 caracters";
                txbNom.Text = "ERROR";
            }
            else
            {
                errNom.Text = "";
            }
            validaInsert();
        }

        private void txbDescripcio_TextChanged(object sender, TextChangedEventArgs e)
        {
            if (txbDescripcio.Text.Length > 75 || txbDescripcio.Text=="ERROR")
            {
                errDesc.Text = "Max 75 caracters";
                txbDescripcio.Text = "ERROR";
            }
            else
            {
                errDesc.Text = "";
            }
            validaInsert();
        }

        private void txbPreu_TextChanged(object sender, TextChangedEventArgs e)
        {
            try
            {
                decimal preu = Decimal.Parse(txbPreu.Text);
                if (preu > 999 || txbPreu.Text == "0")
                {
                    errPreu.Text = "No superior a 999";
                    txbPreu.Text = "0";
                }
                else
                {
                    errPreu.Text = "";
                }

            }catch(Exception ex)
            {
                errPreu.Text = "Mal format";
                txbPreu.Text = "0";
            }
            validaInsert();
        }

        private void cmbDisponible_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            validaInsert();
        }

        private void cmbCategories_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            validaInsert();
        }
        private async void btnFile1_Click()
        {
            FileOpenPicker fp = new FileOpenPicker();
            fp.FileTypeFilter.Add(".jpg");
            fp.FileTypeFilter.Add(".png");
            StorageFile sf = await fp.PickSingleFileAsync();
            if (sf != null)
            {
                var folder = ApplicationData.Current.LocalFolder;
                var iconsFolder = await folder.CreateFolderAsync("icons", CreationCollisionOption.OpenIfExists);
                string name = (DateTime.Now).ToString("yyyyMMddhhmmss") + "_" + sf.Name;
                StorageFile copiedFile = await sf.CopyAsync(iconsFolder, name);
                BitmapImage tmpBitmap = new BitmapImage(new Uri(copiedFile.Path));
                image =tmpBitmap;
                fotoNouPlat.Source = image;
                validaInsert();
            }
        }

        private void validaInsert()
        {
            if(txbDescripcio.Text.Length>0 && txbDescripcio.Text!= "ERROR" && txbNom.Text.Length > 0 &&
               txbNom.Text != "ERROR" && txbPreu.Text.Length > 0 && txbPreu.Text != "0" && 
               cmbCategories.SelectedValue!=null && cmbDisponible.SelectedValue != null && image!=null)
            {
                btnInserirPlat.IsEnabled = true;
            }
            else
            {
                btnInserirPlat.IsEnabled = false;
            }
        }

        private void natejar()
        {
            txbDescripcio.Text = "";
            txbNom.Text = "";
            txbPreu.Text = "";
            cmbCategories.SelectedValue = null;
            cmbDisponible.SelectedValue = null;
            fotoNouPlat.Source = null;
            lsvCategories.SelectedValue = null;
        }
    }
}
