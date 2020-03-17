package sample.TwoDD.Class;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageScan
{
    public ImageScan(String path, String[] FilesBMP, int Save)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
        BufferedImage image = null;
        try

        {
            image = ImageIO.read(new File(path, FilesBMP[0]));
        } catch(IOException e)
        {
            e.printStackTrace();
        }

        // явно указываем расширение файла для простоты реализации
        try
        {
            ImageIO.write(image, "bmp", baos);
        } catch(IOException e)

        {
            e.printStackTrace();
        }
        try

        {
            baos.flush();
        } catch(IOException e)
        {
            e.printStackTrace();
        }

        String base64String = Base64.encode(baos.toByteArray());
        try

        {
            baos.close();
        } catch(IOException e)
        {
            e.printStackTrace();
        }

        // декодируем полученную строку в массив байт
        byte[] resByteArray = Base64.decode(base64String);

        // считываем полученный массив в объект BufferedImage
        BufferedImage resultImage = null;
        try
        {
            resultImage = ImageIO.read(new ByteArrayInputStream(resByteArray));
        } catch(
                IOException e)

        {
            e.printStackTrace();
        }
    }
}
