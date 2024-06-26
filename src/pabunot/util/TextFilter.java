package pabunot.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * Provides a {@code DocumentFilter} that restricts and validates text input based on specified {@link DataType}.
 * This filter can be applied to components like {@link javax.swing.JTextField} or {@link javax.swing.JTextArea}
 * to enforce specific content rules such as numerical limits, character types, or string lengths.
 *
 * @see javax.swing.text.DocumentFilter
 * @see javax.swing.JTextField
 * @see javax.swing.JTextArea
 *
 * @Citation:  <a href="https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers">Restricting JTextField input to Integers [duplicate] | Stack Overflow</a>
 * @User: <a href="https://stackoverflow.com/users/522444/hovercraft-full-of-eels">Hovercraft Full Of Eels | Stack Overflow</a>
 * @Modifier: <a href="https://github.com/iid3rp">derp :3 | GitHub</a>
 */
public class TextFilter extends DocumentFilter
{
    private long minimumNumber;
    private long numberLimit;
    public DataType dataType; // the string equivalent of the enum
    public int characterLimit = 0;
    private int currentNumber;

    public TextFilter()
    {
        this.dataType = DataType.TYPE_STRING;
    }   
    
    // constructor with @parameters
    public TextFilter(DataType d)
    {
        this.dataType = d;
        characterLimit = Integer.MAX_VALUE;
        minimumNumber = Long.MIN_VALUE;
        numberLimit = Long.MAX_VALUE;
    }

    public TextFilter(DataType d, int charLimit)
    {
        this.dataType = d;
        characterLimit = charLimit;
        minimumNumber = Long.MIN_VALUE;
        numberLimit = Long.MAX_VALUE;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException 
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);
        
        if (test(sb.toString())) 
        {
            super.insertString(fb, offset, string, attr);
        }
    }

    private boolean test(String text) 
    {
        if (text.isEmpty()) 
        {
            return true; // Allow blank document
        }
        try 
        {
            switch(dataType)
            {
                // default choice
                case TYPE_STRING:
                {
                    if(text.length() <= characterLimit)
                        break;
                    else return false;
                }
                
                case TYPE_NUMERICAL:
                {
                    long x = Long.parseLong(text);
                    if(text.length() <= characterLimit)
                        break;
                    else return false;
                }
                
                // currency based
                case TYPE_CURRENCY:
                {
                    if(text.length() <= characterLimit)
                    {
                        Double.parseDouble(text);

                        String[] parts = text.split("\\.");
                        if (parts.length > 1)
                        {
                            // Check if the fractional part has more than two digits
                            String fractionalPart = parts[1];
                            if (fractionalPart.length() > 2)
                            {
                                return false; // More than two decimal places
                            }
                        }
                        if(text.toLowerCase().contains("d") || text.toLowerCase().contains("f"))
                        {
                            return false;
                        }
                    }
                    else return false;

                    break;
                }
                case TYPE_CHARACTERS_ONLY:
                {
                    if(text.length() <= characterLimit)
                    {
                        if(!text.matches("[a-zA-Z]+"))
                        {
                            return false;
                        }
                    }
                    else return false;
                    break;
                }
                default:
                {
                    throw new IllegalArgumentException("Unknown data type: " + dataType);
                }
            }
        
            return true;
        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException 
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);
    
        if(test(sb.toString())) 
        {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException 
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.delete(offset, offset + length);
    
        if (test(sb.toString())) 
        {
           super.remove(fb, offset, length);
        }
    }
}